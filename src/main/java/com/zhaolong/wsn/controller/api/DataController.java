package com.zhaolong.wsn.controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.TTCCLayout;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zhaolong.wsn.entity.Data;
import com.zhaolong.wsn.entity.Node;
import com.zhaolong.wsn.service.DataService;
import com.zhaolong.wsn.service.NodeService;

import lombok.SneakyThrows;
class NodeData extends Data{
	public String level;
	public String conclusion;
	public String advice;
	public String updateTime;
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
@Controller
@RequestMapping(value = "/api/*") 
public class DataController {
	
	@Autowired
	private DataService dataService;
	
	@Autowired
	private NodeService nodeService;
	
	// 获取某一个节点的最新的一条数据
	@RequestMapping(value = "node_latest_data/{node_id}", method = RequestMethod.GET)
	public @ResponseBody NodeData nodeLatetData(HttpServletRequest request, HttpServletResponse response, @PathVariable("node_id") Long node_id) {
		Data data = dataService.latestData(node_id);
		NodeData nData = new NodeData();
		if(data != null){
			nData.setPm25((double)Math.round(data.getPm25()*100)/100);
			nData.setPm10((double)Math.round(data.getPm10()*100)/100);
			nData.setAqi((double)Math.round(data.getAqi()*100)/100);
			nData.setUpdateTime(String.valueOf(data.getDataDate()) + " " + data.getDataTime());
			nData.setLevel(getDesc(data.getAqi()));
			nData.setConclusion(getConclusion(data.getAqi()));
			nData.setAdvice(getAdvice(data.getAqi()));
		}
		return nData;
	}
	// 获取每个节点的当日数据和最近一月的日平均数据
	@RequestMapping(value = "data_list/{node_id}", method = RequestMethod.GET)
	public @ResponseBody List<ArrayList<Double>> dataList(HttpServletRequest request, HttpServletResponse response, @PathVariable("node_id") Long node_id) {
		String requestType = "day";
		try{
			requestType = request.getQueryString().split("=")[1];
		}catch (Exception e) {
			// TODO: handle exception
		}
		List<ArrayList<Double>> ret = new ArrayList<ArrayList<Double>>();
//		对象序列化报错，所以按照每一个参数计算一个数组进行存储，比如PM2.5最近24小时的是一个有24个值的数组，最近一个月是30个值的数组	
//		首先获取该节点的所有数据
		Date date = new Date();
		java.sql.Date today = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
		if(requestType.equals("day")){
			Time timeNow = Time.valueOf("00:00:00"); 
//			today 是今天的时间，比如2017-11-24
//			timeNow 是一天开始的时分秒时间 如00:00:00
			List<Data> datas = dataService.dataList(node_id, today);
			ArrayList<Double> pm25 = new ArrayList<Double>();
			ArrayList<Double> pm10 = new ArrayList<Double>();
			ArrayList<Double> so2 = new ArrayList<Double>();
			ArrayList<Double> no2 = new ArrayList<Double>();
			ArrayList<Double> co = new ArrayList<Double>();
			ArrayList<Double> o3 = new ArrayList<Double>();
			ArrayList<Double> aqi = new ArrayList<Double>();
			ArrayList<Double> hours = new ArrayList<Double>();
//			首先计算今日的24条记录
			int dataSize = datas.size();
			for(int h = 0; h < new Date().getHours(); ++h){
				Calendar calendar =new GregorianCalendar();  
				calendar.setTime(timeNow);
				calendar.add(calendar.HOUR, h);
				Time tTime = new Time(calendar.getTime().getTime()); 
				int tCnt = 0;
				double tPm25Sum = 0, tPm10Sum = 0, tSo2Sum = 0, tNo2Sum = 0,tO3Sum = 0,tCoSum = 0,tAqiSum = 0;
				for(int i = 0; i < dataSize; ++i){
					Data tmp = datas.get(i);
					if(tmp.getDataTime().getHours() == tTime.getHours()){
						tCnt += 1;
						tPm25Sum += tmp.getPm25();
						tPm10Sum += tmp.getPm10();
						tSo2Sum += tmp.getSo2();
						tNo2Sum += tmp.getNo2();
						tO3Sum += tmp.getO3();
						tCoSum += tmp.getCo();
						tAqiSum += tmp.getAqi();
					}
					if(tmp.getDataTime().getHours() > tTime.getHours()){
						break;
					}
				}
				if(tCnt > 0){
					pm25.add(tPm25Sum / tCnt);
					pm10.add(tPm10Sum / tCnt);
					so2.add(tSo2Sum / tCnt);
					no2.add(tNo2Sum / tCnt);
					o3.add(tO3Sum / tCnt);
					co.add(tCoSum / tCnt);
					aqi.add(tAqiSum / tCnt);
				}else{
					pm25.add(null);
					pm10.add(null);
					so2.add(null);
					no2.add(null);
					o3.add(null);
					co.add(null);
					aqi.add(null);
				}
				hours.add((double)h);
			}
			ret.add(pm25);
			ret.add(pm10);
			ret.add(so2);
			ret.add(no2);
			ret.add(co);
			ret.add(o3);
			ret.add(aqi);
			ret.add(hours);
			return ret;
		}else if(requestType.equals("month")){
			// 计算最近三十天的数据，从当前日期往前退30天，先获取这个区间的该节点的数据，然后从第一天到第三十天筛选数据，通过计算每一天的统计值，然后新增一个数组记录每一条数据对应的日期的月份
			Calendar calendar =new GregorianCalendar();  
			calendar.setTime(today);  
			calendar.add(calendar.DATE, -30);  
			java.sql.Date preMonth = new java.sql.Date(calendar.getTime().getTime());
			List<Data> datas = dataService.dataList(node_id, preMonth, today);
			ArrayList<Double> pm25 = new ArrayList<Double>();
			ArrayList<Double> pm10 = new ArrayList<Double>();
			ArrayList<Double> so2 = new ArrayList<Double>();
			ArrayList<Double> no2 = new ArrayList<Double>();
			ArrayList<Double> co = new ArrayList<Double>();
			ArrayList<Double> o3 = new ArrayList<Double>();
			ArrayList<Double> aqi = new ArrayList<Double>();
			ArrayList<Double> days = new ArrayList<Double>();
			ArrayList<Double> months = new ArrayList<Double>();
			int dataSize = datas.size();
			java.sql.Date start = preMonth;
			for(int day = 0; day < 30; day++){
				Calendar tmp =new GregorianCalendar();  
				tmp.setTime(preMonth);  
				tmp.add(calendar.DATE, day);  
				java.sql.Date tmpDay = new java.sql.Date(tmp.getTime().getTime());
				int tCnt = 0;
				double tPm25Sum = 0, tPm10Sum = 0, tSo2Sum = 0, tNo2Sum = 0,tO3Sum = 0,tCoSum = 0,tAqiSum = 0;
				for(int i = 0; i < dataSize; ++i){
					Data tmpData = datas.get(i);
					if(tmpData.getDataDate().getMonth() == tmpDay.getMonth() && tmpData.getDataDate().getDate() == tmpDay.getDate()){
						tCnt += 1;
						tPm25Sum += tmpData.getPm25();
						tPm10Sum += tmpData.getPm10();
						tSo2Sum += tmpData.getSo2();
						tNo2Sum += tmpData.getNo2();
						tO3Sum += tmpData.getO3();
						tCoSum += tmpData.getCo();
						tAqiSum += tmpData.getAqi();
					}
					if(tmpData.getDataDate().after(tmpDay)){
						break;
					}
				}
				if(tCnt > 0){
					pm25.add(tPm25Sum / tCnt);
					pm10.add(tPm10Sum / tCnt);
					so2.add(tSo2Sum / tCnt);
					no2.add(tNo2Sum / tCnt);
					o3.add(tO3Sum / tCnt);
					co.add(tCoSum / tCnt);
					aqi.add(tAqiSum / tCnt);
				}else{
					pm25.add(null);
					pm10.add(null);
					so2.add(null);
					no2.add(null);
					o3.add(null);
					co.add(null);
					aqi.add(null);
				}
				days.add((double)tmpDay.getDate());
				months.add((double) tmpDay.getMonth());
			}
			ret.add(pm25);
			ret.add(pm10);
			ret.add(so2);
			ret.add(no2);
			ret.add(co);
			ret.add(o3);
			ret.add(aqi);
			ret.add(days);
			ret.add(months);
			return ret;
		}else{
			return new ArrayList<ArrayList<Double>>();
		}
	}
	// 获取排名数据，针对所有的安装节点，按照今日，昨日，一周，一月分别计算当天，昨天，一周，一月的平均AQI指数并返回
	@RequestMapping(value = "ranking_list", method = RequestMethod.GET)
	public @ResponseBody List<NodeRank> rankingList(HttpServletRequest request, HttpServletResponse response) {
		String requestType = "day";
		List<NodeRank> rankList = new ArrayList<NodeRank>();
		try{
			requestType = request.getQueryString().split("=")[1];
		}catch (Exception e) {
			// TODO: handle exception
		}
		if(requestType.equals("day")){
			// 按照今日数据排序的时候，需要对每个节点获取今日的数据，然后计算平均值，按照平均值进行排序
			// 首先获取所有的节点数据
			List<Node> nodeList = nodeService.nodeList();
			// 然后获取今日的节点的数据
			List<Data> dataList = new ArrayList<Data>();
			Date date = new Date();
			java.sql.Date today = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
			Calendar calendar = new GregorianCalendar();  
			calendar.setTime(today);  
			calendar.add(calendar.DATE, 1);  
			java.sql.Date tomorrow = new java.sql.Date(calendar.getTime().getTime());
			dataList = dataService.dataList(today, tomorrow);
			
			int len = nodeList.size();
			int dataLen = dataList.size();
			for(int i = 0; i < len; ++i){
				for(int j = 0; j < dataLen; ++j){
					if(dataList.get(j).getNodeId() == nodeList.get(i).getId()){
						Node node = nodeList.get(i);
						Data data = dataList.get(j);
						NodeRank nodeRank = new NodeRank();
						nodeRank.setAddress(node.getAddress());
						nodeRank.setAQI((double)Math.round(data.getAqi()*100)/100);
						nodeRank.setCity(node.getCity());
						nodeRank.setCreated(node.getCreated());
						nodeRank.setId(node.getId());
						nodeRank.setInstallTime(node.getInstallTime());
						nodeRank.setLatitude(node.getLatitude());
						nodeRank.setLongitude(node.getLongitude());
						nodeRank.setNodeName(node.getNodeName());
						nodeRank.setOnline(node.getOnline());
						nodeRank.setProvince(node.getProvince());
						nodeRank.setSo2(data.getSo2());
						nodeRank.setPm25((double)Math.round(data.getPm25()*100)/100);
						nodeRank.setPm10(data.getPm10());
						rankList.add(nodeRank);
						break;
					}
				}
			}
//			Collections.sort(rankList, new SortByAqi());
//			int rankLen = rankList.size();
//			for (int i = 0; i < rankLen; ++i) {
//				rankList.get(i).setRank(i+1);
//				rankList.get(i).setDataDesc(getDesc(rankList.get(i).getAQI()));
//		    }
//			return rankList;
		}else if(requestType.equals("yesterday")){
			// 按照昨日数据排序的时候，需要对每个节点获取昨日的数据，然后计算平均值，按照平均值进行排序
			// 首先获取所有的节点数据
			List<Node> nodeList = nodeService.nodeList();
			// 然后获取昨日的节点的数据
			List<Data> dataList = new ArrayList<Data>();
			Date date = new Date();
			java.sql.Date today = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
			Calendar calendar = new GregorianCalendar();  
			calendar.setTime(today);  
			calendar.add(calendar.DATE, -1);  
			java.sql.Date yesterday = new java.sql.Date(calendar.getTime().getTime());
			dataList = dataService.dataList(yesterday, today);
			
			int len = nodeList.size();
			int dataLen = dataList.size();
			for(int i = 0; i < len; ++i){
				double aqiSum = 0;
				int cnt = 0;
				for(int j = 0; j < dataLen; ++j){
					if(dataList.get(j).getNodeId() == nodeList.get(i).getId()){
						Data data = dataList.get(j);
						aqiSum += data.getAqi();
						cnt += 1;
					}
				}
				Node node = nodeList.get(i);
				NodeRank nodeRank = new NodeRank();
				nodeRank.setAddress(node.getAddress());
				nodeRank.setCity(node.getCity());
				nodeRank.setCreated(node.getCreated());
				nodeRank.setId(node.getId());
				nodeRank.setInstallTime(node.getInstallTime());
				nodeRank.setLatitude(node.getLatitude());
				nodeRank.setLongitude(node.getLongitude());
				nodeRank.setNodeName(node.getNodeName());
				nodeRank.setOnline(node.getOnline());
				nodeRank.setProvince(node.getProvince());
				if(cnt > 0){
					nodeRank.setAQI((double)Math.round(aqiSum/cnt*100)/100);
				}else{
					nodeRank.setAQI(0);
				}
				rankList.add(nodeRank);
			}
//			Collections.sort(rankList, new SortByAqi());
//			int rankLen = rankList.size();
//			for (int i = 0; i < rankLen; ++i) {
//				rankList.get(i).setRank(i+1);
//				rankList.get(i).setDataDesc(getDesc(rankList.get(i).getAQI()));
//		    }
//			return rankList;
		}else if(requestType.equals("week")){
			// 按照一周数据排序的时候，需要对每个节点获取最近一周的数据，然后计算平均值，按照平均值进行排序
			// 首先获取所有的节点数据
			List<Node> nodeList = nodeService.nodeList();
			// 然后获取最近一周的节点的数据
			List<Data> dataList = new ArrayList<Data>();
			Date date = new Date();
			java.sql.Date today = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
			Calendar calendar = new GregorianCalendar();  
			calendar.setTime(today);  
			calendar.add(calendar.DATE, -7);  
			java.sql.Date yesterday = new java.sql.Date(calendar.getTime().getTime());
			dataList = dataService.dataList(yesterday, today);
			int len = nodeList.size();
			int dataLen = dataList.size();
			for(int i = 0; i < len; ++i){
				double aqiSum = 0;
				int cnt = 0;
				for(int j = 0; j < dataLen; ++j){
					if(dataList.get(j).getNodeId() == nodeList.get(i).getId()){
						Data data = dataList.get(j);
						aqiSum += data.getAqi();
						cnt += 1;
					}
				}
				Node node = nodeList.get(i);
				NodeRank nodeRank = new NodeRank();
				nodeRank.setAddress(node.getAddress());
				nodeRank.setCity(node.getCity());
				nodeRank.setCreated(node.getCreated());
				nodeRank.setId(node.getId());
				nodeRank.setInstallTime(node.getInstallTime());
				nodeRank.setLatitude(node.getLatitude());
				nodeRank.setLongitude(node.getLongitude());
				nodeRank.setNodeName(node.getNodeName());
				nodeRank.setOnline(node.getOnline());
				nodeRank.setProvince(node.getProvince());
				if(cnt > 0){
					nodeRank.setAQI((double)Math.round(aqiSum/cnt*100)/100);
				}else{
					nodeRank.setAQI(0);
				}
				rankList.add(nodeRank);
			}
//			Collections.sort(rankList, new SortByAqi());
//			int rankLen = rankList.size();
//			for (int i = 0; i < rankLen; ++i) {
//				rankList.get(i).setRank(i+1);
//				rankList.get(i).setDataDesc(getDesc(rankList.get(i).getAQI()));
//		    }
//			return rankList;
		}else if(requestType.equals("month")){
			// 按照一月数据排序的时候，需要对每个节点获取最近一月的数据，然后计算平均值，按照平均值进行排序
			// 首先获取所有的节点数据
			List<Node> nodeList = nodeService.nodeList();
			// 然后获取最近一月的节点的数据
			List<Data> dataList = new ArrayList<Data>();
			Date date = new Date();
			java.sql.Date today = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
			Calendar calendar = new GregorianCalendar();  
			calendar.setTime(today);  
			calendar.add(calendar.DATE, -30);  
			java.sql.Date yesterday = new java.sql.Date(calendar.getTime().getTime());
			dataList = dataService.dataList(yesterday, today);
			int len = nodeList.size();
			int dataLen = dataList.size();
			for(int i = 0; i < len; ++i){
				double aqiSum = 0;
				int cnt = 0;
				for(int j = 0; j < dataLen; ++j){
					if(dataList.get(j).getNodeId() == nodeList.get(i).getId()){
						Data data = dataList.get(j);
						aqiSum += data.getAqi();
						cnt += 1;
					}
				}
				Node node = nodeList.get(i);
				NodeRank nodeRank = new NodeRank();
				nodeRank.setAddress(node.getAddress());
				nodeRank.setCity(node.getCity());
				nodeRank.setCreated(node.getCreated());
				nodeRank.setId(node.getId());
				nodeRank.setInstallTime(node.getInstallTime());
				nodeRank.setLatitude(node.getLatitude());
				nodeRank.setLongitude(node.getLongitude());
				nodeRank.setNodeName(node.getNodeName());
				nodeRank.setOnline(node.getOnline());
				nodeRank.setProvince(node.getProvince());
				if(cnt > 0){
					nodeRank.setAQI((double)Math.round(aqiSum/cnt*100)/100);
				}else{
					nodeRank.setAQI(0);
				}
				rankList.add(nodeRank);
			}
//			Collections.sort(rankList, new SortByAqi());
//			int rankLen = rankList.size();
//			for (int i = 0; i < rankLen; ++i) {
//				rankList.get(i).setRank(i+1);
//				rankList.get(i).setDataDesc(getDesc(rankList.get(i).getAQI()));
//		    }
//			return rankList;
		}else{
			// 不合法参数
		}
		int rankLen = rankList.size();
		for (int i = 0; i < rankList.size(); ++i) {
			if(rankList.get(i).AQI <= 0){
				rankList.remove(i);
				i --;
			}
		}
		Collections.sort(rankList, new SortByAqi());
		for (int i = 0; i < rankList.size(); ++i) {
			rankList.get(i).setRank(i+1);
			rankList.get(i).setDataDesc(getDesc(rankList.get(i).getAQI()));
	    }
		return rankList;
	}
	@RequestMapping(value = "data_save", method = RequestMethod.GET)
	public void dataSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("data_save api");
		for(int i = 26; i <= 30; ++i){
			for(int j = 0; j < 24; ++j){
				java.sql.Date dataDate = new java.sql.Date(2017, 10, i);
				Time dataTime = new Time(j, 0, 0);
				System.out.println("data_save: "+dataDate + " " + dataTime);
				Data d = new Data();
				Random random=new Random();
				d.setPm25(random.nextDouble() * 1000);
				d.setPm10(random.nextDouble() * 1000);
				d.setSo2(random.nextDouble() * 1000);
				d.setNo2(random.nextDouble() * 1000);
				d.setO3(random.nextDouble() * 1000);
				d.setCo(random.nextDouble() * 1000);
				d.setAqi(random.nextDouble() * 1000);
				d.setDataDate(dataDate);
				d.setDataTime(dataTime);
				d.setNodeId((long) random.nextInt(20));
				dataService.saveData(d);
			}
		}		
		response.setStatus(200);
		PrintWriter pWriter = response.getWriter();
		pWriter.println("success");
	}
	
	public String getDesc(double aqi){
		if(aqi <= 0){
			return "无数据";
		}
		if(aqi < 50){
			return "优";
		}else if(aqi < 100){
			return "良";
		}else if(aqi < 150){
			return "轻度污染";
		}else if(aqi < 200){
			return "中度污染";
		}else if(aqi < 300){
			return "重度污染";
		}else if(aqi > 300){
			return "严重污染";
		}
		return "无数据";
	}
	public String getConclusion(double aqi){
		if(aqi <= 0){
			return "无数据";
		}
		if(aqi < 50){
			return "空气质量令人满意，基本无空气污染";
		}else if(aqi < 100){
			return "空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响";
		}else if(aqi < 150){
			return "易感人群症状有轻度加剧，健康人群出现刺激状况";
		}else if(aqi < 200){
			return "进一步加剧易感人群症状，可能对健康人群心脏，呼吸系统有影响";
		}else if(aqi < 300){
			return "心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状";
		}else if(aqi > 300){
			return "健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病";
		}
		return "无数据";
	}
	public String getAdvice(double aqi){
		if(aqi <= 0){
			return "无数据";
		}
		if(aqi < 50){
			return "各类人群可正常活动";
		}else if(aqi < 100){
			return "极少数异常敏感人群应减少户外活动";
		}else if(aqi < 150){
			return "儿童，老年人及心脏病，呼吸系统疾病患者应减少长时间，高强度的户外训练";
		}else if(aqi < 200){
			return "儿童，老年人及心脏病，呼吸系统疾病患者避免长时间，高强度的户外训练，一般人群适量减少户外运动";
		}else if(aqi < 300){
			return "儿童，老年人及心脏病，呼吸系统疾病患者应停留在室内，停止户外运动，一般人群减少户外运动";
		}else if(aqi > 300){
			return "儿童，老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外运动";
		}
		return "无数据";
	}
}