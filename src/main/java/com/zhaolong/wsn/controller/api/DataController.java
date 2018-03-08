package com.zhaolong.wsn.controller.api;

import java.io.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhaolong.wsn.entity.Data;
import com.zhaolong.wsn.entity.Node;
import com.zhaolong.wsn.service.DataService;
import com.zhaolong.wsn.service.NodeService;

import lombok.SneakyThrows;
// NodeData从Data扩展而来，新增了关于AQI指数的解释信息，同时用来对数据进行统计（小时，日周月年）
class NodeData extends Data{
	public String nodeName;
	public String nodeAddress;
	public String level;
	public int pollutionLevelNumber;
	public String classification;
	public String conclusion;
	public String advice;
	public String updateTime; // 数据平均之后的时间戳
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeAddress() {
		return nodeAddress;
	}
	public void setNodeAddress(String nodeAddress) {
		this.nodeAddress = nodeAddress;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
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
	public int getPollutionLevelNumber() { return pollutionLevelNumber; }
	public void setPollutionLevelNumber(int pollutionLevelNumber) { this.pollutionLevelNumber = pollutionLevelNumber;}
}
class WarningData{
	public String nodeName;
	public String nodeAddress;
	public String dataType;
	public String parameter;
	public String warningTime;
	public String content;

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeAddress() {
		return nodeAddress;
	}

	public void setNodeAddress(String nodeAddress) {
		this.nodeAddress = nodeAddress;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getWarningTime() {
		return warningTime;
	}

	public void setWarningTime(String warningTime) {
		this.warningTime = warningTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getWarningData(Data data, String parameter, int dataType){
		if(dataType == 1){
			int cnt = 0;
			// 检查是否是数据异常
			if(parameter.equals("pm25")){
				if(data.getPm25() == null){
					this.setParameter("pm25");
					this.setDataType("异常");
					this.setContent("pm25传感器无数据");
					cnt += 1;
				}
			}else if(parameter.equals("pm10")){
				if(data.getPm10() == null){
					this.setParameter("pm10");
					this.setDataType("异常");
					this.setContent("pm10传感器无数据");
					cnt += 1;
				}
			}else if(parameter.equals("so2")){
				if(data.getSo2() == null){
					this.setParameter("so2");
					this.setDataType("异常");
					this.setContent("so2传感器无数据");
					cnt += 1;
				}
			}else if(parameter.equals("no2")){
				if(data.getNo2() == null){
					this.setParameter("no2");
					this.setDataType("异常");
					this.setContent("no2传感器无数据");
					cnt += 1;
				}
			}else if(parameter.equals("co")){
				if(data.getCo() == null){
					this.setParameter("co");
					this.setDataType("异常");
					this.setContent("co传感器无数据");
					cnt += 1;
				}
			}else if(parameter.equals("o3")){
				if(data.getO3() == null){
					this.setParameter("o3");
					this.setDataType("异常");
					this.setContent("o3传感器无数据");
					cnt += 1;
				}
			}else if(parameter.equals("speed")){
				if(data.getWindSpeed() == null){
					this.setParameter("speed");
					this.setDataType("异常");
					this.setContent("speed传感器无数据");
					cnt += 1;
				}
			}else if(parameter.equals("direction")){
				if(data.getWindyDirection() == null){
					this.setParameter("direction");
					this.setDataType("异常");
					this.setContent("direction传感器无数据");
					cnt += 1;
				}
			}else if(parameter.equals("humidity")){
				if(data.getAirHumidity() == null){
					this.setParameter("humidity");
					this.setDataType("异常");
					this.setContent("humidity传感器无数据");
					cnt += 1;
				}
			}
			this.setWarningTime(String.valueOf(data.getDataDate()) + " " + data.getDataTime());
			if(cnt > 0){
				return true;
			}
			return false;
		}else if(dataType == 2){
			// 检查是否是数据超出阈值
			int cnt = 0;
			if(parameter.equals("pm25")){
				if(data.getPm25() > 150){
					this.setParameter("pm25");
					this.setDataType("预警");
					this.setContent("pm25数据超过150");
					cnt += 1;
				}
			}else if(parameter.equals("pm10")){
				if(data.getPm10()  > 150){
					this.setParameter("pm10");
					this.setDataType("预警");
					this.setContent("pm10数据超过150");
					cnt += 1;
				}
			}else if(parameter.equals("so2")){
				if(data.getSo2() > 150){
					this.setParameter("so2");
					this.setDataType("预警");
					this.setContent("so2数据超过150");
					cnt += 1;
				}
			}else if(parameter.equals("no2")){
				if(data.getNo2()  > 150l){
					this.setParameter("no2");
					this.setDataType("预警");
					this.setContent("no2数据超过150");
					cnt += 1;
				}
			}else if(parameter.equals("co")){
				if(data.getCo()  > 150){
					this.setParameter("co");
					this.setDataType("预警");
					this.setContent("co数据超过150");
					cnt += 1;
				}
			}else if(parameter.equals("o3")){
				if(data.getO3()  > 150){
					this.setParameter("o3");
					this.setDataType("预警");
					this.setContent("o3数据超过150");
					cnt += 1;
				}
			}
			this.setWarningTime(String.valueOf(data.getDataDate()) + " " + data.getDataTime());
			if(cnt > 0){
				return true;
			}
			return false;
		}
		return false;
	}
}
@Controller
@RequestMapping(value = "/api/*") 
public class DataController {
	/*
		node_latest_data_list：获取所有设备的最新的一条数据
		node_latest_data/{node_id}：获取某个节点的最新的一条数据
		data_list/{node_id}"：获取某个节点的当日数据和最近一月的日平均数据
		ranking_list：获取排名数据，针对所有的安装节点，按照今日，昨日，一周，一月分别计算当天，昨天，一周，一月的平均AQI指数并返回
		data_save：生成测试数据
		node_historical_data：获取某个节点的历史数据，根据后缀参数判断是获取全部数据(requestType=all)/小时数据(requestType=hour)/日平均数据(requestType=day)
		node_list_aqi: 获取每个节点最新的一条数据计算该节点的污染指数的空气质量信息
		node_warning_list: 获取某一个节点的在指定日期的预警和异常数据
	 */
	@Autowired
	private DataService dataService;
	
	@Autowired
	private NodeService nodeService;

	// 获取所有设备的最新的一条数据（有多少个设备就对应多少条数据）
	@RequestMapping(value = "node_latest_data_list", method = RequestMethod.GET)
	public @ResponseBody List<NodeData> nodeLatestDataList(HttpServletRequest request, HttpServletResponse response) {
		List<Node> nodeList = nodeService.nodeList();
		List<NodeData> nodeData = new ArrayList<NodeData>();
		for(int i = 0; i < nodeList.size(); ++i){
			Data data = dataService.latestData(nodeList.get(i).getId());
			NodeData nData = new NodeData();
			if(data != null){
				nData.setNodeId(nodeList.get(i).getId());
				nData.setNodeName(nodeList.get(i).getNodeName());
				nData.setNodeAddress(nodeList.get(i).getProvince() + nodeList.get(i).getCity());
				try{
					nData.setPm25((double)Math.round(data.getPm25()*100)/100);
				}catch (Exception e){
				}
				try{
					nData.setPm10((double)Math.round(data.getPm10()*100)/100);
				}catch (Exception e){

				}
				try{
					nData.setSo2((double)Math.round(data.getSo2()*100)/100);
				}catch (Exception e){

				}

				try{
					nData.setNo2((double)Math.round(data.getNo2()*100)/100);
				}catch (Exception e){

				}
				try{
					nData.setCo((double)Math.round(data.getCo()*100)/100);
				}catch (Exception e){

				}
				try{
					nData.setO3((double)Math.round(data.getO3()*100)/100);
				}catch (Exception e){

				}
				try{
					nData.setAqi((double)Math.round(data.getAqi()*100)/100);
				}catch (Exception e){

				}
				try{
					nData.setUpdateTime(String.valueOf(data.getDataDate()) + " " + data.getDataTime());
					nData.setClassification(getClassification(data.getAqi()));
					nData.setConclusion(getConclusion(data.getAqi()));
					nData.setAdvice(getAdvice(data.getAqi()));
				}catch (Exception e){

				}
			}
			nodeData.add(nData);
		}
		return nodeData;
	}

	// 获取某一个节点的最新的一条数据
	@RequestMapping(value = "node_latest_data/{node_id}", method = RequestMethod.GET)
	public @ResponseBody NodeData nodeLatetData(HttpServletRequest request, HttpServletResponse response, @PathVariable("node_id") Long node_id) {
		Data data = dataService.latestData(node_id);
		NodeData nData = new NodeData();
		if(data != null){
			try {
				nData.setPm25((double) Math.round(data.getPm25() * 100) / 100);
			}catch (Exception e) {}
			try{ nData.setPm10((double)Math.round(data.getPm10()*100)/100); } catch (Exception e){}
			try{ nData.setAqi((double)Math.round(data.getAqi()*100)/100); } catch (Exception e){}
			nData.setUpdateTime(String.valueOf(data.getDataDate()) + " " + data.getDataTime());

			try {
				nData.setClassification(getClassification(data.getAqi()));
				nData.setConclusion(getConclusion(data.getAqi()));
				nData.setAdvice(getAdvice(data.getAqi()));
			}catch (Exception e){

			}
		}
		return nData;
	}

	// 获取某个节点的当日数据和最近一月的日平均数据
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
						try{
							tPm25Sum += tmp.getPm25();
						}catch (Exception e) {
							// TODO: handle exception
						}
						try{
							tPm10Sum += tmp.getPm10();
						}catch (Exception e) {
							// TODO: handle exception
						}
						try{
							tSo2Sum += tmp.getSo2();
						}catch (Exception e) {
							// TODO: handle exception
						}
						try{
							tNo2Sum += tmp.getNo2();
						}catch (Exception e) {
							// TODO: handle exception
						}
						try{
							tO3Sum += tmp.getO3();
						}catch (Exception e) {
							// TODO: handle exception
						}
						try{
							tCoSum += tmp.getCo();
						}catch (Exception e) {
							// TODO: handle exception
						}
						try{
							tAqiSum += tmp.getAqi();
						}catch (Exception e) {
							// TODO: handle exception
						}
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
						try{
							tPm25Sum += tmpData.getPm25();
						}catch (Exception e) {
							// TODO: handle exception
						}
						try{
							tPm10Sum += tmpData.getPm10();
						}catch (Exception e) {
							// TODO: handle exception
						}
						try{
							tSo2Sum += tmpData.getSo2();
						}catch (Exception e) {
							// TODO: handle exception
						}
						try{
							tNo2Sum += tmpData.getNo2();
						}catch (Exception e) {
							// TODO: handle exception
						}
						try{
							tO3Sum += tmpData.getO3();
						}catch (Exception e) {
							// TODO: handle exception
						}
						try{
							tCoSum += tmpData.getCo();
						}catch (Exception e) {
							// TODO: handle exception
						}
						try{
							tAqiSum += tmpData.getAqi();
						}catch (Exception e) {
							// TODO: handle exception
						}
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
		// 计算所有节点在不同时间段的数据排名情况，根据调用的参数选择不同的操作数据。优化：利用时间段筛选所有的节点的数据，然后按照数据节点进行匹配筛选，然后计算平均值，然后进行排名
		String requestType = "day";
		List<NodeRank> rankList = new ArrayList<NodeRank>();
		try{
			requestType = request.getQueryString().split("=")[1];
		}catch (Exception e) {
			// TODO: handle exception
		}
		List<Node> nodeList = nodeService.nodeList();
		List<Data> dataList = new ArrayList<Data>();

		if(requestType.equals("day")){
			// 按照今日数据排序的时候，需要对每个节点获取今日的数据，然后计算平均值，按照平均值进行排序
			// 首先获取所有的节点数据
			// 然后获取今日的节点的数据
			Date date = new Date();
			java.sql.Date today = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
			Calendar calendar = new GregorianCalendar();  
			calendar.setTime(today);  
			calendar.add(calendar.DATE, 1);  
			java.sql.Date tomorrow = new java.sql.Date(calendar.getTime().getTime());
			dataList = dataService.dataList(today, tomorrow);
		}else if(requestType.equals("yesterday")){
			// 按照昨日数据排序的时候，需要对每个节点获取昨日的数据，然后计算平均值，按照平均值进行排序
			// 首先获取所有的节点数据
			// 然后获取昨日的节点的数据
			Date date = new Date();
			java.sql.Date today = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
			Calendar calendar = new GregorianCalendar();  
			calendar.setTime(today);  
			calendar.add(calendar.DATE, -1);  
			java.sql.Date yesterday = new java.sql.Date(calendar.getTime().getTime());
			dataList = dataService.dataList(yesterday, today);
		}else if(requestType.equals("week")){
			// 按照一周数据排序的时候，需要对每个节点获取最近一周的数据，然后计算平均值，按照平均值进行排序
			// 首先获取所有的节点数据
			// 然后获取最近一周的节点的数据
			Date date = new Date();
			java.sql.Date today = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
			Calendar calendar = new GregorianCalendar();  
			calendar.setTime(today);  
			calendar.add(calendar.DATE, -7);  
			java.sql.Date yesterday = new java.sql.Date(calendar.getTime().getTime());
			dataList = dataService.dataList(yesterday, today);

		}else if(requestType.equals("month")){
			// 按照一月数据排序的时候，需要对每个节点获取最近一月的数据，然后计算平均值，按照平均值进行排序
			// 首先获取所有的节点数据
			// 然后获取最近一月的节点的数据
			Date date = new Date();
			java.sql.Date today = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
			Calendar calendar = new GregorianCalendar();  
			calendar.setTime(today);  
			calendar.add(calendar.DATE, -30);  
			java.sql.Date yesterday = new java.sql.Date(calendar.getTime().getTime());
			dataList = dataService.dataList(yesterday, today);
		}else{
			// 不合法参数
		}
		int len = nodeList.size();
		int dataLen = dataList.size();
		for(int i = 0; i < len; ++i){
			double aqiSum = 0, pm25Sum = 0, pm10Sum = 0;
			int pm25Cnt = 0, aqiCnt = 0, pm10Cnt = 0;
			for(int j = 0; j < dataLen; ++j){
				if(dataList.get(j).getNodeId().equals(nodeList.get(i).getId())){
					Data data = dataList.get(j);
					try{
						aqiSum += data.getAqi();
						aqiCnt += 1;
					}catch (Exception e){}
					try{
						pm25Sum += data.getPm25();
						pm25Cnt += 1;
					}catch (Exception e){}
					try{
						pm10Sum += data.getPm10();
						pm10Cnt += 1;
					}catch (Exception e){}
				}
			}
			Node node = nodeList.get(i);
			NodeRank nodeRank = new NodeRank();
			try {nodeRank.setAddress(node.getAddress());}catch (Exception e){}
			try {nodeRank.setCity(node.getCity());}catch (Exception e){}
			try {nodeRank.setCreated(node.getCreated());}catch (Exception e){}
			try {nodeRank.setId(node.getId());}catch (Exception e){}
			try {nodeRank.setInstallTime(node.getInstallTime());}catch (Exception e){}
			try {nodeRank.setLatitude(node.getLatitude());}catch (Exception e){}
			try {nodeRank.setLongitude(node.getLongitude());}catch (Exception e){}
			try {nodeRank.setNodeName(node.getNodeName());}catch (Exception e){}
			try {nodeRank.setOnline(node.getOnline());}catch (Exception e){}
			try {nodeRank.setProvince(node.getProvince());}catch (Exception e){}
			if(aqiCnt > 0){
				try {
					nodeRank.setAQI((double) Math.round(aqiSum / aqiCnt * 100) / 100);
				} catch (Exception e){
					nodeRank.setAQI(-1);
				}
			}else{
				// 如果AQI参数不存在，则尝试尝试将pm25的值赋给aqi
				if(pm25Cnt > 0){
					try {
						nodeRank.setAQI((double) Math.round(pm25Sum / pm25Cnt * 100) / 100);
						nodeRank.setPm25((double) Math.round(pm25Sum / pm25Cnt * 100) / 100);
					} catch (Exception e){
						nodeRank.setPm25(-1);
					}
				}else{
					// 如果PM25参数不存在，则尝试用pm10的值赋给aqi
					if(pm10Cnt > 0){
						try {
							nodeRank.setAQI((double) Math.round(pm10Sum / pm10Cnt * 100) / 100);
							nodeRank.setPm10((double) Math.round(pm10Sum / pm10Cnt * 100) / 100);
						} catch (Exception e){
							nodeRank.setPm10(-1);
						}
					}else{
						// 都不存在
						nodeRank.setPm10(-1);
					}
				}
			}
			if(aqiCnt <= 0 && pm25Cnt <= 0 && pm10Cnt <= 0) {
				// 都不存在丢失这条数据
			}else{
			rankList.add(nodeRank);
			}
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
			rankList.get(i).setDataDesc(getClassification(rankList.get(i).getAQI()));
			rankList.get(i).setPollutionLevelNumber(getPollutionLevelNumber(rankList.get(i).getAQI()));
	    }
		return rankList;
	}

	// 获取某个节点的历史数据，根据后缀参数判断是获取全部数据(requestType=all)/小时数据(requestType=hour)/日平均数据(requestType=day)
	@RequestMapping(value = "node_historical_data/{node_id}", method = RequestMethod.POST)
	public @ResponseBody List<NodeData> nodeHistoricalData(HttpServletRequest request, HttpServletResponse response, @PathVariable("node_id") Long node_id) {
		String requestType = "all";
		List<NodeData> nodeDataList = new ArrayList<NodeData>();
		// 对于需要获取的数据区间，先给一个默认值，即今天的日期
		Date date = new Date();
		java.sql.Date startDay = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDay);
		calendar.add(calendar.DATE, 1);
		java.sql.Date endDay = new java.sql.Date(calendar.getTime().getTime());

		// 针对查询区间的小时和日平均数据提供java.Date类型的默认值
		Date javaStartTime = new Date();
		javaStartTime = new Date(javaStartTime.getYear(), javaStartTime.getMonth(), javaStartTime.getDate());
		calendar.setTime(javaStartTime);
		calendar.add(calendar.DATE, 1);
		Date javaEndTime = new Date(calendar.getTime().getTime());

		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Test:--->" + request.getParameter("requestType"));
			requestType=request.getParameter("requestType") == null ? requestType :request.getParameter("requestType");
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
//			System.out.println("requestType :" + requestType);
//			System.out.println("startTime :" + startTime);
//			System.out.println("endTime :" + endTime);
			if(startTime != null){
				startDay = new java.sql.Date(dateFormat.parse(startTime).getYear(), dateFormat.parse(startTime).getMonth(), dateFormat.parse(startTime).getDate());;
				// 如果提供了查询的区间的起止日期，将改日期转换成前面声明的java.Date类型的数据
				javaStartTime = dateFormat.parse(startTime);
			}
			if(endTime != null){
				endDay = new java.sql.Date(dateFormat.parse(endTime).getYear(), dateFormat.parse(endTime).getMonth(), dateFormat.parse(endTime).getDate());
				// 如果提供了查询的区间的起止日期，将改日期转换成前面声明的java.Date类型的数据
				javaEndTime = dateFormat.parse(endTime);
			}

		}catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
		System.out.println("requestType: " + requestType);
		System.out.println("startDay: " + startDay);
		System.out.println("endDay: " + endDay);
		Node currentNde = nodeService.nodeInfo(node_id);
//		System.out.println("requestType :" + requestType.equals("all"));
		if(requestType.equals("all")){
//			System.out.println("requestType is all :" + requestType.equals("all"));
			// 获取该节点的所有数据，将Data类型的数据拷贝到NodeData
			List<Data> tmpList = dataService.dataList(node_id, startDay, endDay);
			int dataLen = tmpList.size();
			for(int i = 0; i < dataLen; ++i){
				Data data = tmpList.get(i);
				NodeData nodeData = new NodeData();
				nodeData.setNodeName(currentNde.getNodeName());
				nodeData.setDataStatus(data.getDataStatus());
				nodeData.setPm25(data.getPm25());
				nodeData.setPm10(data.getPm10());
				nodeData.setSo2(data.getSo2());
				nodeData.setNo2(data.getSo2());
				nodeData.setCo(data.getCo());
				nodeData.setO3(data.getO3());
				nodeData.setUpdateTime(String.valueOf(data.getDataDate()) + " " + data.getDataTime());
				nodeDataList.add(nodeData);
			}
		}else if(requestType.equals("hour")){
			List<Data> tmpList = dataService.dataList(node_id, startDay, endDay);
			int dataLen = tmpList.size();
			// 此时获取该节点的指定日期的的小时平均的数据
			// 按照日期从选择的结束日期的前一天的23点开始倒计时计算获取对应的一小时之内的数据平均值
			while(javaStartTime.before(javaEndTime)){
				Calendar tmpCalendar = new GregorianCalendar();
				tmpCalendar.setTime(javaStartTime);
				tmpCalendar.add(tmpCalendar.HOUR, 1);

				Date pointStart = javaStartTime;
				Date pointEnd = new Date(tmpCalendar.getTime().getTime());
				// pointStart是当前小时，pointEnd是当前小时的下一小时
				javaStartTime = pointEnd;

				java.sql.Date sqlPointStartDate = new java.sql.Date(pointStart.getYear(), pointStart.getMonth(), pointStart.getDate());
				java.sql.Time sqlPointStartTime = new java.sql.Time(pointStart.getHours(), 0,0);
//				System.out.println("sqlPointStartDate:" + sqlPointStartDate);
//				System.out.println("sqlPointStartDate:" + sqlPointStartDate);
				// 此时获得pointStart和pointEnd作为筛选条件筛选当前节点的在该时间段内的数据的平均值
				int hourDataCnt = 0;
				NodeData nodeData = new NodeData();
				double pm25Total = 0.0;
				int pm25Cnt = 0;
				double pm10Total = 0.0;
				int pm10Cnt = 0;
				double so2Total = 0.0;
				int so2Cnt = 0;
				double no2Total = 0.0;
				int no2Cnt = 0;
				double coTotal = 0.0;
				int coCnt = 0;
				double o3Total = 0.0;
				int o3Cnt = 0;
				double humidityTotal = 0.0;
				int humidityCnt = 0;
				double speedTotal = 0.0;
				int speedCnt = 0;
				for (Data tData : tmpList) {
					java.sql.Date dataDate = tData.getDataDate();
					Time dataTime = tData.getDataTime();
					if (dataDate.equals(sqlPointStartDate) && dataTime.getHours() == sqlPointStartTime.getHours()) {
						hourDataCnt += 1;
						if(tData.getPm25() != null){
							pm25Cnt += 1;
							pm25Total += tData.getPm25();
						}
						if(tData.getPm10() != null){
							pm10Cnt += 1;
							pm10Total += tData.getPm10();
						}
						if(tData.getSo2() != null){
							so2Cnt += 1;
							so2Total += tData.getSo2();
						}
						if(tData.getNo2() != null){
							no2Cnt += 1;
							no2Total += tData.getNo2();
						}
						if(tData.getCo() != null){
							coCnt += 1;
							coTotal += tData.getCo();
						}
						if(tData.getO3() != null){
							o3Cnt += 1;
							o3Total += tData.getO3();
						}
						if(tData.getAirHumidity() != null){
							humidityCnt += 1;
							humidityTotal += tData.getAirHumidity();
						}
						if(tData.getWindSpeed() != null){
							speedCnt += 1;
							speedTotal += tData.getWindSpeed();
						}
					}
				}
				if(hourDataCnt > 0) {
					nodeData.setNodeName(currentNde.getNodeName());
					nodeData.setPm25(pm25Cnt > 0 ? pm25Total / pm25Cnt : null);
					nodeData.setPm10(pm10Cnt > 0 ? pm10Total / pm10Cnt : null);
					nodeData.setSo2(so2Cnt > 0 ? so2Total / so2Cnt : null);
					nodeData.setNo2(no2Cnt > 0 ? no2Total / no2Cnt : null);
					nodeData.setCo(coCnt > 0 ? coTotal / coCnt : null);
					nodeData.setO3(o3Cnt > 0 ? o3Total / o3Cnt : null);
					nodeData.setAirHumidity(humidityCnt > 0 ? humidityTotal / humidityCnt : null);
					nodeData.setWindSpeed(speedCnt > 0 ? speedTotal / speedCnt : null);
					nodeData.setUpdateTime(String.valueOf(sqlPointStartDate) + " " + sqlPointStartTime);
					nodeDataList.add(nodeData);
				}else{
					nodeData.setNodeName(currentNde.getNodeName());
					nodeData.setUpdateTime(String.valueOf(sqlPointStartDate) + " " + sqlPointStartTime);
					nodeDataList.add(nodeData);
				}
			}
		}else if(requestType.equals("day")){
			List<Data> tmpList = dataService.dataList(node_id, startDay, endDay);
			int dataLen = tmpList.size();
			// 此时获取该节点的指定日期的的日平均的数据
			while(javaStartTime.before(javaEndTime)){
				Calendar tmpCalendar = new GregorianCalendar();
				tmpCalendar.setTime(javaStartTime);
				tmpCalendar.add(tmpCalendar.DATE, 1);
				Date pointStart = javaStartTime;
				Date pointEnd = new Date(tmpCalendar.getTime().getTime());
				javaStartTime = pointEnd;
				java.sql.Date sqlPointStartDate = new java.sql.Date(pointStart.getYear(), pointStart.getMonth(), pointStart.getDate());
//				System.out.println("sqlPointStartDate:" + sqlPointStartDate);
				// 此时获得pointStart和pointEnd作为筛选条件筛选当前节点的在该时间段内的数据的平均值
				int dayDataCnt = 0;
				NodeData nodeData = new NodeData();
				double pm25Total = 0.0;
				int pm25Cnt = 0;
				double pm10Total = 0.0;
				int pm10Cnt = 0;
				double so2Total = 0.0;
				int so2Cnt = 0;
				double no2Total = 0.0;
				int no2Cnt = 0;
				double coTotal = 0.0;
				int coCnt = 0;
				double o3Total = 0.0;
				int o3Cnt = 0;
				double humidityTotal = 0.0;
				int humidityCnt = 0;
				double speedTotal = 0.0;
				int speedCnt = 0;
				for (Data tData : tmpList) {
					java.sql.Date dataDate = tData.getDataDate();
					if (dataDate.equals(sqlPointStartDate)) {
						dayDataCnt += 1;
						if(tData.getPm25() != null){
							pm25Cnt += 1;
							pm25Total += tData.getPm25();
						}
						if(tData.getPm10() != null){
							pm10Cnt += 1;
							pm10Total += tData.getPm10();
						}
						if(tData.getSo2() != null){
							so2Cnt += 1;
							so2Total += tData.getSo2();
						}
						if(tData.getNo2() != null){
							no2Cnt += 1;
							no2Total += tData.getNo2();
						}
						if(tData.getCo() != null){
							coCnt += 1;
							coTotal += tData.getCo();
						}
						if(tData.getO3() != null){
							o3Cnt += 1;
							o3Total += tData.getO3();
						}
						if(tData.getAirHumidity() != null){
							humidityCnt += 1;
							humidityTotal += tData.getAirHumidity();
						}
						if(tData.getWindSpeed() != null){
							speedCnt += 1;
							speedTotal += tData.getWindSpeed();
						}
					}
				}
				if(dayDataCnt > 0) {
					nodeData.setPm25(pm25Cnt > 0 ? pm25Total / pm25Cnt : null);
					nodeData.setPm10(pm10Cnt > 0 ? pm10Total / pm10Cnt : null);
					nodeData.setSo2(so2Cnt > 0 ? so2Total / so2Cnt : null);
					nodeData.setNo2(no2Cnt > 0 ? no2Total / no2Cnt : null);
					nodeData.setCo(coCnt > 0 ? coTotal / coCnt : null);
					nodeData.setO3(o3Cnt > 0 ? o3Total / o3Cnt : null);
					nodeData.setAirHumidity(humidityCnt > 0 ? humidityTotal / humidityCnt : null);
					nodeData.setWindSpeed(speedCnt > 0 ? speedTotal / speedCnt : null);
					nodeData.setUpdateTime(String.valueOf(sqlPointStartDate));
					nodeDataList.add(nodeData);
				}else{
					nodeData.setNodeName(currentNde.getNodeName());
					nodeData.setUpdateTime(String.valueOf(sqlPointStartDate));
					nodeDataList.add(nodeData);
				}
			}
		}else{
			// 不合法参数
		}
		Collections.reverse(nodeDataList);
		return nodeDataList;
	}

    // 获取所有节点的历史数据，根据后缀参数判断是获取全部数据(requestType=all)/小时数据(requestType=hour)/日平均数据(requestType=day)
    @RequestMapping(value = "all_nodes_historical_data", method = RequestMethod.POST)
    public @ResponseBody List<ArrayList<NodeData>> nodeHistoricalData(HttpServletRequest request, HttpServletResponse response)  throws IOException, ParseException {
		String nodeId = request.getParameter("nodeId");
		String requestType = request.getParameter("requestType");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		// 首先对数据类型做判断，如果没有默认值，则默认全部数据
		if(requestType == null){
			requestType = "all";
		}
		// 然后针对选择的日期进行处理
		java.sql.Date startDay = null;
		java.sql.Date endDay = null;
		Date javaStartTime = null;
		Date javaEndTime = null;
		if(startTime == null || endTime == null){
			// 如果没有传递数据区间，则给一个默认值，即今天的日期
			Date date = new Date();
			startDay = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(startDay);
			calendar.add(calendar.DATE, 1);
			endDay = new java.sql.Date(calendar.getTime().getTime());
			// 针对查询区间的小时和日平均数据提供java.Date类型的默认值
			javaStartTime = new Date();
			javaStartTime = new Date(javaStartTime.getYear(), javaStartTime.getMonth(), javaStartTime.getDate());
			calendar.setTime(javaStartTime);
			calendar.add(calendar.DATE, 1);
			javaEndTime = new Date(calendar.getTime().getTime());
		}else{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			startDay = new java.sql.Date(dateFormat.parse(startTime).getYear(), dateFormat.parse(startTime).getMonth(), dateFormat.parse(startTime).getDate());
			javaStartTime = dateFormat.parse(startTime);

			endTime=request.getParameter("endTime");
			endDay = new java.sql.Date(dateFormat.parse(endTime).getYear(), dateFormat.parse(endTime).getMonth(), dateFormat.parse(endTime).getDate());
			javaEndTime = dateFormat.parse(endTime);
		}

		List<Node> nodeList = new ArrayList<Node>();
		List<Data> dataLists = new ArrayList<Data>();
		if(nodeId == null){
			return null;
		}else if(Long.parseLong(nodeId) == 0){
			// 导出全部节点的数据，导出全部数据，则先通过时间区间将对应的数据筛选出来，然后再在Java代码中做匹配，降低打开数据库操作的耗时
			dataLists = dataService.dataList(startDay, endDay);
			nodeList = nodeService.nodeList();
		}else{
			// 导出特定站点的数据
			Long currentNodeId = Long.parseLong(nodeId);
			dataLists = dataService.dataList(currentNodeId, startDay, endDay);
			nodeList.add(nodeService.nodeInfo(currentNodeId));
		}
		// dataLists 代表所有节点在日期内的数据， nodeList 代表采集节点
		// 在此做一个归一化处理，即将单站点的数据导出理解为导出所有站点，但所有站点也只有当前这个节点，进行合并处理
		List<ArrayList<NodeData>> nodesDataList = new ArrayList<ArrayList<NodeData>>();
		for(int nI = 0; nI < nodeList.size(); ++nI){
			Date javaStartTimeTmp = javaStartTime;
			Date javaEndTimeTmp = javaEndTime;
			List<Data> nodeOriginalDataList = new ArrayList<Data>(); // 先从大数据数组中筛选出该节点的数据存储到该数组
			nodeOriginalDataList.clear();
			ArrayList<NodeData> nodeDataList = new ArrayList<NodeData>();
			Node currentNode = nodeList.get(nI);
			int datasLen = dataLists.size();
			for(int di = 0; di < datasLen; ++di){
				if(dataLists.get(di).getNodeId().equals(currentNode.getId())){
					// 当前处理数据的设备ID和目标节点ID匹配，则是目节点标数据，加入到当前数组
					nodeOriginalDataList.add(dataLists.get(di));
				}
			}
			if(requestType.equals("all")) {
				// 获取该节点的所有数据，将Data类型的数据拷贝到NodeData
				for (Data data : nodeOriginalDataList) {
					NodeData nodeData = new NodeData();
					nodeData.setNodeName(currentNode.getNodeName());
					nodeData.setDataStatus(data.getDataStatus());
					nodeData.setPm25(data.getPm25());
					nodeData.setPm10(data.getPm10());
					nodeData.setSo2(data.getSo2());
					nodeData.setNo2(data.getSo2());
					nodeData.setCo(data.getCo());
					nodeData.setO3(data.getO3());
					nodeData.setAqi(data.getAqi());
					nodeData.setUpdateTime(String.valueOf(data.getDataDate()) + " " + data.getDataTime());
					nodeDataList.add(nodeData);
				}
			}else if(requestType.equals("hour")){
				// 此时获取该节点的指定日期的的小时平均的数据
				// 按照日期从选择的结束日期的前一天的23点开始倒计时计算获取对应的一小时之内的数据平均值
				while(javaStartTimeTmp.before(javaEndTimeTmp)){
					Calendar tmpCalendar = new GregorianCalendar();
					tmpCalendar.setTime(javaStartTimeTmp);
					tmpCalendar.add(tmpCalendar.HOUR, 1);

					Date pointStart = javaStartTimeTmp;
					Date pointEnd = new Date(tmpCalendar.getTime().getTime());
					// pointStart是当前小时，pointEnd是当前小时的下一小时
					javaStartTimeTmp = pointEnd;

					java.sql.Date sqlPointStartDate = new java.sql.Date(pointStart.getYear(), pointStart.getMonth(), pointStart.getDate());
					java.sql.Time sqlPointStartTime = new java.sql.Time(pointStart.getHours(), 0,0);
					// 此时获得pointStart和pointEnd作为筛选条件筛选当前节点的在该时间段内的数据的平均值
					int hourDataCnt = 0;
					NodeData nodeData = new NodeData();
					nodeData.setNodeName(currentNode.getNodeName());
					nodeData.setUpdateTime(String.valueOf(sqlPointStartDate) + " " + sqlPointStartTime);
					double pm25Total = 0.0, pm10Total = 0.0, so2Total = 0.0, no2Total = 0.0, coTotal = 0.0, o3Total = 0.0, humidityTotal = 0.0, speedTotal = 0.0;
					int pm25Cnt = 0,  pm10Cnt = 0, so2Cnt = 0, no2Cnt = 0, coCnt = 0, o3Cnt = 0, humidityCnt = 0, speedCnt = 0;
					for (Data tData : nodeOriginalDataList) {
						java.sql.Date dataDate = tData.getDataDate();
						Time dataTime = tData.getDataTime();
						if (dataDate.equals(sqlPointStartDate) && dataTime.getHours() == sqlPointStartTime.getHours()) {
							hourDataCnt += 1;
							if(tData.getPm25() != null){ pm25Cnt += 1; pm25Total += tData.getPm25(); }
							if(tData.getPm10() != null){ pm10Cnt += 1; pm10Total += tData.getPm10(); }
							if(tData.getSo2() != null){ so2Cnt += 1; so2Total += tData.getSo2(); }
							if(tData.getNo2() != null){ no2Cnt += 1; no2Total += tData.getNo2(); }
							if(tData.getCo() != null){ coCnt += 1; coTotal += tData.getCo(); }
							if(tData.getO3() != null){ o3Cnt += 1; o3Total += tData.getO3(); }
							if(tData.getAirHumidity() != null){ humidityCnt += 1; humidityTotal += tData.getAirHumidity(); }
							if(tData.getWindSpeed() != null){ speedCnt += 1; speedTotal += tData.getWindSpeed(); }
						}
					}
					if(hourDataCnt > 0) {
						nodeData.setPm25(pm25Cnt > 0 ? pm25Total / pm25Cnt : null);
						nodeData.setPm10(pm10Cnt > 0 ? pm10Total / pm10Cnt : null);
						nodeData.setSo2(so2Cnt > 0 ? so2Total / so2Cnt : null);
						nodeData.setNo2(no2Cnt > 0 ? no2Total / no2Cnt : null);
						nodeData.setCo(coCnt > 0 ? coTotal / coCnt : null);
						nodeData.setO3(o3Cnt > 0 ? o3Total / o3Cnt : null);
						nodeData.setAirHumidity(humidityCnt > 0 ? humidityTotal / humidityCnt : null);
						nodeData.setWindSpeed(speedCnt > 0 ? speedTotal / speedCnt : null);
					}
					nodeDataList.add(nodeData);

				}
			}else if(requestType.equals("day")){
				// 此时获取该节点的指定日期的的日平均的数据
				while(javaStartTimeTmp.before(javaEndTimeTmp)){
					Calendar tmpCalendar = new GregorianCalendar();
					tmpCalendar.setTime(javaStartTimeTmp);
					tmpCalendar.add(tmpCalendar.DATE, 1);
					Date pointStart = javaStartTimeTmp;
					Date pointEnd = new Date(tmpCalendar.getTime().getTime());
					javaStartTimeTmp = pointEnd;
					java.sql.Date sqlPointStartDate = new java.sql.Date(pointStart.getYear(), pointStart.getMonth(), pointStart.getDate());
					// 此时获得pointStart和pointEnd作为筛选条件筛选当前节点的在该时间段内的数据的平均值
					int dayDataCnt = 0;
					NodeData nodeData = new NodeData();
					nodeData.setNodeName(currentNode.getNodeName());
					nodeData.setUpdateTime(String.valueOf(sqlPointStartDate));
					double pm25Total = 0.0, pm10Total = 0.0, so2Total = 0.0, no2Total = 0.0, coTotal = 0.0, o3Total = 0.0, humidityTotal = 0.0, speedTotal = 0.0;
					int pm25Cnt = 0,  pm10Cnt = 0, so2Cnt = 0, no2Cnt = 0, coCnt = 0, o3Cnt = 0, humidityCnt = 0, speedCnt = 0;
					for (Data tData : nodeOriginalDataList) {
						java.sql.Date dataDate = tData.getDataDate();
						if (dataDate.equals(sqlPointStartDate)) {
							dayDataCnt += 1;
							if(tData.getPm25() != null){ pm25Cnt += 1; pm25Total += tData.getPm25(); }
							if(tData.getPm10() != null){ pm10Cnt += 1; pm10Total += tData.getPm10(); }
							if(tData.getSo2() != null){ so2Cnt += 1; so2Total += tData.getSo2(); }
							if(tData.getNo2() != null){ no2Cnt += 1; no2Total += tData.getNo2(); }
							if(tData.getCo() != null){ coCnt += 1; coTotal += tData.getCo(); }
							if(tData.getO3() != null){ o3Cnt += 1; o3Total += tData.getO3(); }
							if(tData.getAirHumidity() != null){ humidityCnt += 1; humidityTotal += tData.getAirHumidity(); }
							if(tData.getWindSpeed() != null){ speedCnt += 1; speedTotal += tData.getWindSpeed(); }
						}
					}
					if(dayDataCnt > 0) {
						nodeData.setPm25(pm25Cnt > 0 ? pm25Total / pm25Cnt : null);
						nodeData.setPm10(pm10Cnt > 0 ? pm10Total / pm10Cnt : null);
						nodeData.setSo2(so2Cnt > 0 ? so2Total / so2Cnt : null);
						nodeData.setNo2(no2Cnt > 0 ? no2Total / no2Cnt : null);
						nodeData.setCo(coCnt > 0 ? coTotal / coCnt : null);
						nodeData.setO3(o3Cnt > 0 ? o3Total / o3Cnt : null);
						nodeData.setAirHumidity(humidityCnt > 0 ? humidityTotal / humidityCnt : null);
						nodeData.setWindSpeed(speedCnt > 0 ? speedTotal / speedCnt : null);
					}
					nodeDataList.add(nodeData);

				}
			}else{
				// 不合法参数
			}
			nodesDataList.add(nodeDataList);
		}
        Collections.reverse(nodesDataList);
        return nodesDataList;
    }

	// 获取每个节点最新的一条数据计算该节点的污染指数的空气质量信息
	@RequestMapping(value = "node_list_aqi", method = RequestMethod.POST)
	public @ResponseBody List<NodeData> nodeListAqi(HttpServletRequest request, HttpServletResponse response){
		List<Node> nodeList = nodeService.nodeList();
		List<NodeData> nodeData = new ArrayList<NodeData>();
		for(int i = 0; i < nodeList.size(); ++i){
			Data data = dataService.latestData(nodeList.get(i).getId());
			NodeData nData = new NodeData();
			if(data != null){
				data.setAqi(data.getPm25());
				try{
					nData.setNodeId(nodeList.get(i).getId());
				}catch (Exception e){

				}
				try{
					nData.setNodeName(nodeList.get(i).getNodeName());
				}catch (Exception e){
				}
				try{
					nData.setNodeAddress(nodeList.get(i).getProvince() + nodeList.get(i).getCity());
				}catch (Exception e){
				}
				try{
					nData.setPm25((double)Math.round(data.getPm25()*100)/100);
				}catch (Exception e){
				}
				try{
					nData.setPm10((double)Math.round(data.getPm10()*100)/100);
				}catch (Exception e){
				}
				try{
					nData.setSo2((double)Math.round(data.getSo2()*100)/100);
				}catch (Exception e){
				}
				try{
					nData.setNo2((double)Math.round(data.getNo2()*100)/100);
				}catch (Exception e){
				}
				try{
					nData.setCo((double)Math.round(data.getCo()*100)/100);
				}catch (Exception e){
				}
				try{
					nData.setO3((double)Math.round(data.getO3()*100)/100);
				}catch (Exception e){
				}
				try{
					nData.setAqi((double)Math.round(data.getAqi()*100)/100);
				}catch (Exception e){
				}
				try{
					nData.setUpdateTime(String.valueOf(data.getDataDate()) + " " + data.getDataTime());
					nData.setLevel(getLevel(data.getAqi()));
					nData.setClassification(getClassification(data.getAqi()));
					nData.setConclusion(getConclusion(data.getAqi()));
					nData.setAdvice(getAdvice(data.getAqi()));
					nData.setPollutionLevelNumber(getPollutionLevelNumber(data.getAqi()));
				}catch (Exception e){
				}
			}
			nodeData.add(nData);
		}
		return nodeData;
	}

	// 获取某一个节点的在指定日期的预警和异常数据
	@RequestMapping(value = "node_warning_list", method = RequestMethod.POST)
	public @ResponseBody List<WarningData> nodeWarningList(HttpServletRequest request, HttpServletResponse response){
		String nodeId = null;
		List<WarningData> nodeWarningList = new ArrayList<WarningData>();
		// 对于需要获取的数据区间，先给一个默认值，即今天的日期
		Date date = new Date();
		java.sql.Date startDay = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDay);
		calendar.add(calendar.DATE, 1);
		java.sql.Date endDay = new java.sql.Date(calendar.getTime().getTime());
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			nodeId=request.getParameter("nodeId");
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
//			System.out.println("nodeId :" + nodeId);
//			System.out.println("startTime :" + startTime);
//			System.out.println("endTime :" + endTime);
			startDay = new java.sql.Date(dateFormat.parse(startTime).getYear(), dateFormat.parse(startTime).getMonth(), dateFormat.parse(startTime).getDate());;
			endDay = new java.sql.Date(dateFormat.parse(endTime).getYear(), dateFormat.parse(endTime).getMonth(), dateFormat.parse(endTime).getDate());
		}catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
		// 对于需要获取的节点，先根据nodeId获取对应的的节点信息
		if(nodeId == null){
			return null;
		}
		Node node = nodeService.nodeInfo(Long.valueOf(nodeId));
		List<Data> nodeDataList = dataService.dataList(Long.valueOf(nodeId), startDay, endDay);
		int dataLen = nodeDataList.size();
		ArrayList<String> parameterList = new ArrayList<String>();
		parameterList.add("pm25");
		parameterList.add("pm10");
		parameterList.add("so2");
		parameterList.add("no2");
		parameterList.add("co");
		parameterList.add("o3");
		for(int i = 0; i < dataLen; ++i){
			Data tData = nodeDataList.get(i);
			for(int j = 0; j < 6; ++j){
				String p = parameterList.get(j);
				WarningData tmp = new WarningData();
				tmp.setNodeName(node.getNodeName());
				tmp.setNodeAddress(node.getAddress());
				boolean res1 = tmp.getWarningData(tData, p, 1);
				if(res1){
					nodeWarningList.add(tmp);
				}
				WarningData tmp2 = new WarningData();
				tmp2.setNodeName(node.getNodeName());
				tmp2.setNodeAddress(node.getAddress());
				boolean res2 = tmp2.getWarningData(tData, p, 2);
				if(res2){
					nodeWarningList.add(tmp2);
				}
			}
		}
		Collections.reverse(nodeWarningList);
		return nodeWarningList;
	}

	@RequestMapping(value = "data_save", method = RequestMethod.GET)
	public void dataSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("data_save api");
		for(long nodeId = 7; nodeId <= 13; ++nodeId) {
			for (int i = 1; i <= 11; ++i) {
				for (int j = 0; j < 24; ++j) {
					java.sql.Date dataDate = new java.sql.Date(118, 0, i); // year的值是用实际数字减去1900
					Time dataTime = new Time(j, 0, 0);
					System.out.println("data_save: " + dataDate + " " + dataTime);
					Data d = new Data();
					Random random = new Random();
					d.setPm25(random.nextDouble() * 1000);
					d.setPm10(random.nextDouble() * 1000);
					d.setSo2(random.nextDouble() * 1000);
					d.setNo2(random.nextDouble() * 1000);
					d.setO3(random.nextDouble() * 1000);
					d.setCo(random.nextDouble() * 1000);
					d.setAqi(random.nextDouble() * 1000);
					d.setDataDate(dataDate);
					d.setDataTime(dataTime);
					d.setNodeId((long) nodeId);
					dataService.saveData(d);
				}
			}
		}
		response.setStatus(200);
		PrintWriter pWriter = response.getWriter();
		pWriter.println("success");
	}

	// 导出excel
	@RequestMapping(value = "excelExport", method = RequestMethod.GET)
	public String excelExport(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		String nodeId = request.getParameter("nodeId");
		String requestType = request.getParameter("requestType");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		// 首先对数据类型做判断，如果没有默认值，则默认全部数据
		if(requestType == null){
			requestType = "all";
		}
		// 然后针对选择的日期进行处理
		java.sql.Date startDay = null;
		java.sql.Date endDay = null;
		Date javaStartTime = null;
		Date javaEndTime = null;
		if(startTime == null || endTime == null){
			// 如果没有传递数据区间，则给一个默认值，即今天的日期
			Date date = new Date();
			startDay = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(startDay);
			calendar.add(calendar.DATE, 1);
			endDay = new java.sql.Date(calendar.getTime().getTime());
			// 针对查询区间的小时和日平均数据提供java.Date类型的默认值
			javaStartTime = new Date();
			javaStartTime = new Date(javaStartTime.getYear(), javaStartTime.getMonth(), javaStartTime.getDate());
			calendar.setTime(javaStartTime);
			calendar.add(calendar.DATE, 1);
			javaEndTime = new Date(calendar.getTime().getTime());
		}else{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			startDay = new java.sql.Date(dateFormat.parse(startTime).getYear(), dateFormat.parse(startTime).getMonth(), dateFormat.parse(startTime).getDate());
			javaStartTime = dateFormat.parse(startTime);

			endTime=request.getParameter("endTime");
			endDay = new java.sql.Date(dateFormat.parse(endTime).getYear(), dateFormat.parse(endTime).getMonth(), dateFormat.parse(endTime).getDate());
			javaEndTime = dateFormat.parse(endTime);
		}

		List<Node> nodeList = new ArrayList<Node>();
		List<Data> dataLists = new ArrayList<Data>();
		if(nodeId == null){
			return null;
		}else if(Long.parseLong(nodeId) == 0){
			// 导出全部节点的数据，导出全部数据，则先通过时间区间将对应的数据筛选出来，然后再在Java代码中做匹配，降低打开数据库操作的耗时
			dataLists = dataService.dataList(startDay, endDay);
			nodeList = nodeService.nodeList();
		}else{
			// 导出特定站点的数据
			Long currentNodeId = Long.parseLong(nodeId);
			dataLists = dataService.dataList(currentNodeId, startDay, endDay);
			nodeList.add(nodeService.nodeInfo(currentNodeId));
		}
		// dataLists 代表所有节点在日期内的数据， nodeList 代表采集节点
		// 在此做一个归一化处理，即将单站点的数据导出理解为导出所有站点，但所有站点也只有当前这个节点，进行合并处理
		List<ArrayList<NodeData>> nodesDataList = new ArrayList<ArrayList<NodeData>>();
		for(int nI = 0; nI < nodeList.size(); ++nI){
			Date javaStartTimeTmp = javaStartTime;
			Date javaEndTimeTmp = javaEndTime;
			List<Data> nodeOriginalDataList = new ArrayList<Data>(); // 先从大数据数组中筛选出该节点的数据存储到该数组
			nodeOriginalDataList.clear();
			ArrayList<NodeData> nodeDataList = new ArrayList<NodeData>();
			Node currentNode = nodeList.get(nI);
			int datasLen = dataLists.size();
			for(int di = 0; di < datasLen; ++di){
				if(dataLists.get(di).getNodeId().equals(currentNode.getId())){
					// 当前处理数据的设备ID和目标节点ID匹配，则是目节点标数据，加入到当前数组
					nodeOriginalDataList.add(dataLists.get(di));
				}
			}
			if(requestType.equals("all")) {
				// 获取该节点的所有数据，将Data类型的数据拷贝到NodeData
				for (Data data : nodeOriginalDataList) {
					NodeData nodeData = new NodeData();
					nodeData.setNodeName(currentNode.getNodeName());
					nodeData.setDataStatus(data.getDataStatus());
					nodeData.setPm25(data.getPm25());
					nodeData.setPm10(data.getPm10());
					nodeData.setSo2(data.getSo2());
					nodeData.setNo2(data.getSo2());
					nodeData.setCo(data.getCo());
					nodeData.setO3(data.getO3());
					nodeData.setAqi(data.getAqi());
					nodeData.setUpdateTime(String.valueOf(data.getDataDate()) + " " + data.getDataTime());
					nodeDataList.add(nodeData);
				}
			}else if(requestType.equals("hour")){
				// 此时获取该节点的指定日期的的小时平均的数据
				// 按照日期从选择的结束日期的前一天的23点开始倒计时计算获取对应的一小时之内的数据平均值
				while(javaStartTimeTmp.before(javaEndTimeTmp)){
					Calendar tmpCalendar = new GregorianCalendar();
					tmpCalendar.setTime(javaStartTimeTmp);
					tmpCalendar.add(tmpCalendar.HOUR, 1);

					Date pointStart = javaStartTimeTmp;
					Date pointEnd = new Date(tmpCalendar.getTime().getTime());
					// pointStart是当前小时，pointEnd是当前小时的下一小时
					javaStartTimeTmp = pointEnd;

					java.sql.Date sqlPointStartDate = new java.sql.Date(pointStart.getYear(), pointStart.getMonth(), pointStart.getDate());
					java.sql.Time sqlPointStartTime = new java.sql.Time(pointStart.getHours(), 0,0);
					// 此时获得pointStart和pointEnd作为筛选条件筛选当前节点的在该时间段内的数据的平均值
					int hourDataCnt = 0;
					NodeData nodeData = new NodeData();
					nodeData.setNodeName(currentNode.getNodeName());
					nodeData.setUpdateTime(String.valueOf(sqlPointStartDate) + " " + sqlPointStartTime);
					double pm25Total = 0.0, pm10Total = 0.0, so2Total = 0.0, no2Total = 0.0, coTotal = 0.0, o3Total = 0.0, humidityTotal = 0.0, speedTotal = 0.0;
					int pm25Cnt = 0,  pm10Cnt = 0, so2Cnt = 0, no2Cnt = 0, coCnt = 0, o3Cnt = 0, humidityCnt = 0, speedCnt = 0;
					for (Data tData : nodeOriginalDataList) {
						java.sql.Date dataDate = tData.getDataDate();
						Time dataTime = tData.getDataTime();
						if (dataDate.equals(sqlPointStartDate) && dataTime.getHours() == sqlPointStartTime.getHours()) {
							hourDataCnt += 1;
							if(tData.getPm25() != null){ pm25Cnt += 1; pm25Total += tData.getPm25(); }
							if(tData.getPm10() != null){ pm10Cnt += 1; pm10Total += tData.getPm10(); }
							if(tData.getSo2() != null){ so2Cnt += 1; so2Total += tData.getSo2(); }
							if(tData.getNo2() != null){ no2Cnt += 1; no2Total += tData.getNo2(); }
							if(tData.getCo() != null){ coCnt += 1; coTotal += tData.getCo(); }
							if(tData.getO3() != null){ o3Cnt += 1; o3Total += tData.getO3(); }
							if(tData.getAirHumidity() != null){ humidityCnt += 1; humidityTotal += tData.getAirHumidity(); }
							if(tData.getWindSpeed() != null){ speedCnt += 1; speedTotal += tData.getWindSpeed(); }
						}
					}
					if(hourDataCnt > 0) {
						nodeData.setPm25(pm25Cnt > 0 ? pm25Total / pm25Cnt : null);
						nodeData.setPm10(pm10Cnt > 0 ? pm10Total / pm10Cnt : null);
						nodeData.setSo2(so2Cnt > 0 ? so2Total / so2Cnt : null);
						nodeData.setNo2(no2Cnt > 0 ? no2Total / no2Cnt : null);
						nodeData.setCo(coCnt > 0 ? coTotal / coCnt : null);
						nodeData.setO3(o3Cnt > 0 ? o3Total / o3Cnt : null);
						nodeData.setAirHumidity(humidityCnt > 0 ? humidityTotal / humidityCnt : null);
						nodeData.setWindSpeed(speedCnt > 0 ? speedTotal / speedCnt : null);
					}
					nodeDataList.add(nodeData);

				}
			}else if(requestType.equals("day")){
				// 此时获取该节点的指定日期的的日平均的数据
				while(javaStartTimeTmp.before(javaEndTimeTmp)){
					Calendar tmpCalendar = new GregorianCalendar();
					tmpCalendar.setTime(javaStartTimeTmp);
					tmpCalendar.add(tmpCalendar.DATE, 1);
					Date pointStart = javaStartTimeTmp;
					Date pointEnd = new Date(tmpCalendar.getTime().getTime());
					javaStartTimeTmp = pointEnd;
					java.sql.Date sqlPointStartDate = new java.sql.Date(pointStart.getYear(), pointStart.getMonth(), pointStart.getDate());
					// 此时获得pointStart和pointEnd作为筛选条件筛选当前节点的在该时间段内的数据的平均值
					int dayDataCnt = 0;
					NodeData nodeData = new NodeData();
					nodeData.setNodeName(currentNode.getNodeName());
					nodeData.setUpdateTime(String.valueOf(sqlPointStartDate));
					double pm25Total = 0.0, pm10Total = 0.0, so2Total = 0.0, no2Total = 0.0, coTotal = 0.0, o3Total = 0.0, humidityTotal = 0.0, speedTotal = 0.0;
					int pm25Cnt = 0,  pm10Cnt = 0, so2Cnt = 0, no2Cnt = 0, coCnt = 0, o3Cnt = 0, humidityCnt = 0, speedCnt = 0;
					for (Data tData : nodeOriginalDataList) {
						java.sql.Date dataDate = tData.getDataDate();
						if (dataDate.equals(sqlPointStartDate)) {
							dayDataCnt += 1;
							if(tData.getPm25() != null){ pm25Cnt += 1; pm25Total += tData.getPm25(); }
							if(tData.getPm10() != null){ pm10Cnt += 1; pm10Total += tData.getPm10(); }
							if(tData.getSo2() != null){ so2Cnt += 1; so2Total += tData.getSo2(); }
							if(tData.getNo2() != null){ no2Cnt += 1; no2Total += tData.getNo2(); }
							if(tData.getCo() != null){ coCnt += 1; coTotal += tData.getCo(); }
							if(tData.getO3() != null){ o3Cnt += 1; o3Total += tData.getO3(); }
							if(tData.getAirHumidity() != null){ humidityCnt += 1; humidityTotal += tData.getAirHumidity(); }
							if(tData.getWindSpeed() != null){ speedCnt += 1; speedTotal += tData.getWindSpeed(); }
						}
					}
					if(dayDataCnt > 0) {
						nodeData.setPm25(pm25Cnt > 0 ? pm25Total / pm25Cnt : null);
						nodeData.setPm10(pm10Cnt > 0 ? pm10Total / pm10Cnt : null);
						nodeData.setSo2(so2Cnt > 0 ? so2Total / so2Cnt : null);
						nodeData.setNo2(no2Cnt > 0 ? no2Total / no2Cnt : null);
						nodeData.setCo(coCnt > 0 ? coTotal / coCnt : null);
						nodeData.setO3(o3Cnt > 0 ? o3Total / o3Cnt : null);
						nodeData.setAirHumidity(humidityCnt > 0 ? humidityTotal / humidityCnt : null);
						nodeData.setWindSpeed(speedCnt > 0 ? speedTotal / speedCnt : null);
					}
					nodeDataList.add(nodeData);

				}
			}else{
				// 不合法参数
			}
			nodesDataList.add(nodeDataList);
		}
		// 此时nodesDataList存储的是节点的数据的列表的集合List<ArrayList<NodeData>>
		// 如果是所有数据，则按照节点存储在不同的sheet里面； 如果是小时平均，则按照一个小时一个sheet；如果是日平均则一天一个sheet
		//生成一个Excel文件
		// 创建excel工作簿
		Workbook wb = new HSSFWorkbook();

		String columnNames[]={"device", "dataTime", "pm2.5","pm10","so2","no2","co","o3","AirHumidity", "WindSpeed"};//列名

		if(requestType.equals("all")){
			int nodeLen = nodeList.size();
			for(int nodeI = 0; nodeI < nodeLen; ++nodeI){
				Sheet sheet = wb.createSheet(nodeList.get(nodeI).getNodeName());
				Row rowHead = sheet.createRow((short) (0));
				for(int ip = 0; ip < columnNames.length; ++ip){
					Cell cName = rowHead.createCell(ip);
					cName.setCellValue(columnNames[ip]);
				}
				int nodeDataLen = nodesDataList.get(nodeI).size();
				for(int dataI = 0; dataI < nodeDataLen; ++dataI){
					NodeData tData = nodesDataList.get(nodeI).get(dataI);
					Row row = sheet.createRow((short) (dataI + 1));
					Cell nodeName = row.createCell(0);nodeName.setCellValue(nodeList.get(nodeI).getNodeName());
					Cell dataTime = row.createCell(1);try{ dataTime.setCellValue(tData.getUpdateTime()); } catch (Exception e){dataTime.setCellValue("");}
					Cell pm25 = row.createCell(2);try{ pm25.setCellValue(tData.getPm25()); } catch (Exception e){pm25.setCellValue("");}
					Cell pm10 = row.createCell(3);try{ pm10.setCellValue(tData.getPm10()); } catch (Exception e){pm10.setCellValue("");}
					Cell so2 = row.createCell(4);try{ so2.setCellValue(tData.getSo2()); } catch (Exception e){so2.setCellValue("");}
					Cell no2 = row.createCell(5);try{ no2.setCellValue(tData.getNo2()); } catch (Exception e){no2.setCellValue("");}
					Cell co = row.createCell(6);try{ co.setCellValue(tData.getCo()); } catch (Exception e){co.setCellValue("");}
					Cell o3 = row.createCell(7);try{ o3.setCellValue(tData.getO3()); } catch (Exception e){o3.setCellValue("");}
					Cell AirHumidity = row.createCell(8);try{ AirHumidity.setCellValue(tData.getAirHumidity()); } catch (Exception e){AirHumidity.setCellValue("");}
					Cell WindSpeed = row.createCell(9);try{ WindSpeed.setCellValue(tData.getWindSpeed()); } catch (Exception e){WindSpeed.setCellValue("");}
				}
			}
		}else if(requestType.equals("hour")){
			// 按照一个小时一个sheet则需要对应每一个站点的每一个时间点存储数据，则外层匹配为时间点，里层为节点
			int nodeLen = nodeList.size();
			if(nodesDataList.size() > 0){
				int nodeDataLen = nodesDataList.get(0).size();
				for(int dL = 0; dL < nodeDataLen; ++dL){
					// 代表时间点循环
					String tmp = nodesDataList.get(0).get(dL).getUpdateTime();
					tmp = tmp.replaceAll(":", "-");
					Sheet sheet = wb.createSheet(tmp);
					Row rowHead = sheet.createRow((short) (0));
					for(int ip = 0; ip < columnNames.length; ++ip){
						Cell cName = rowHead.createCell(ip);
						cName.setCellValue(columnNames[ip]);
					}
					for(int nL = 0; nL < nodeLen; ++nL){
						// 代表每一个时间点每个节点的循环
						NodeData tData = nodesDataList.get(nL).get(dL); // 获取某个站点在该时刻的数据
						Row row = sheet.createRow((short) (nL + 1));
						Cell nodeName = row.createCell(0);nodeName.setCellValue(nodeList.get(nL).getNodeName());
						Cell dataTime = row.createCell(1);try{ dataTime.setCellValue(tData.getUpdateTime()); } catch (Exception e){dataTime.setCellValue("");}
						Cell pm25 = row.createCell(2);try{ pm25.setCellValue(tData.getPm25()); } catch (Exception e){pm25.setCellValue("");}
						Cell pm10 = row.createCell(3);try{ pm10.setCellValue(tData.getPm10()); } catch (Exception e){pm10.setCellValue("");}
						Cell so2 = row.createCell(4);try{ so2.setCellValue(tData.getSo2()); } catch (Exception e){so2.setCellValue("");}
						Cell no2 = row.createCell(5);try{ no2.setCellValue(tData.getNo2()); } catch (Exception e){no2.setCellValue("");}
						Cell co = row.createCell(6);try{ co.setCellValue(tData.getCo()); } catch (Exception e){co.setCellValue("");}
						Cell o3 = row.createCell(7);try{ o3.setCellValue(tData.getO3()); } catch (Exception e){o3.setCellValue("");}
						Cell AirHumidity = row.createCell(8);try{ AirHumidity.setCellValue(tData.getAirHumidity()); } catch (Exception e){AirHumidity.setCellValue("");}
						Cell WindSpeed = row.createCell(9);try{ WindSpeed.setCellValue(tData.getWindSpeed()); } catch (Exception e){WindSpeed.setCellValue("");}
					}
				}
			}
		}else if(requestType.equals("day")){
			// 按照一天一个sheet则需要对应每一个站点的每一个时间点存储数据，则外层匹配为时间点，里层为节点
			int nodeLen = nodeList.size();
			if(nodesDataList.size() > 0){
				int nodeDataLen = nodesDataList.get(0).size();
				for(int dL = 0; dL < nodeDataLen; ++dL){
					// 代表时间点循环
					Sheet sheet = wb.createSheet(nodesDataList.get(0).get(dL).getUpdateTime());
					Row rowHead = sheet.createRow((short) (0));
					for(int ip = 0; ip < columnNames.length; ++ip){
						Cell cName = rowHead.createCell(ip);
						cName.setCellValue(columnNames[ip]);
					}
					for(int nL = 0; nL < nodeLen; ++nL){
						// 代表每一个时间点每个节点的循环
						NodeData tData = nodesDataList.get(nL).get(dL); // 获取某个站点在该时刻的数据
						Row row = sheet.createRow((short) (nL + 1));
						Cell nodeName = row.createCell(0);nodeName.setCellValue(nodeList.get(nL).getNodeName());
						Cell dataTime = row.createCell(1);try{ dataTime.setCellValue(tData.getUpdateTime()); } catch (Exception e){dataTime.setCellValue("");}
						Cell pm25 = row.createCell(2);try{ pm25.setCellValue(tData.getPm25()); } catch (Exception e){pm25.setCellValue("");}
						Cell pm10 = row.createCell(3);try{ pm10.setCellValue(tData.getPm10()); } catch (Exception e){pm10.setCellValue("");}
						Cell so2 = row.createCell(4);try{ so2.setCellValue(tData.getSo2()); } catch (Exception e){so2.setCellValue("");}
						Cell no2 = row.createCell(5);try{ no2.setCellValue(tData.getNo2()); } catch (Exception e){no2.setCellValue("");}
						Cell co = row.createCell(6);try{ co.setCellValue(tData.getCo()); } catch (Exception e){co.setCellValue("");}
						Cell o3 = row.createCell(7);try{ o3.setCellValue(tData.getO3()); } catch (Exception e){o3.setCellValue("");}
						Cell AirHumidity = row.createCell(8);try{ AirHumidity.setCellValue(tData.getAirHumidity()); } catch (Exception e){AirHumidity.setCellValue("");}
						Cell WindSpeed = row.createCell(9);try{ WindSpeed.setCellValue(tData.getWindSpeed()); } catch (Exception e){WindSpeed.setCellValue("");}
					}
				}
			}
		}else{

		}

		// 创建第一个sheet（页），并命名
//		Sheet sheet = wb.createSheet("sheetName");
//		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
//		for(int i=0;i<columnNames.length;i++){
//			sheet.setColumnWidth((short) i, (short) (35.7 * 150));
//		}
		// 创建第一行
//		Row row = sheet.createRow((short) 0);
		//设置列名
//		for(int i=0;i<columnNames.length;i++){
//			Cell cell = row.createCell(i);
//			cell.setCellValue(columnNames[i] + "天天");
//		}



		//同理可以设置数据行
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(requestType == "all"){
			response.setHeader("Content-Disposition", "attachment;filename=" + startTime + "-" + endTime + "DeviceData.xls");
		}else if(requestType.equals("hour")){
			response.setHeader("Content-Disposition", "attachment;filename=" + startTime + "-" + endTime + "HourAverage.xls");
		}else{
			response.setHeader("Content-Disposition", "attachment;filename=" + startTime + "-" + endTime + "DayAverage.xls");
		}
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}

	public String getLevel(double aqi){
		if(aqi <= 0){
			return "无数据";
		}
		if(aqi < 50){
			return "一级";
		}else if(aqi < 100){
			return "二级";
		}else if(aqi < 150){
			return "三级";
		}else if(aqi < 200){
			return "四级";
		}else if(aqi < 300){
			return "五级";
		}else if(aqi > 300){
			return "六级";
		}
		return "无数据";
	}
	public String getClassification(double aqi){
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
	public int getPollutionLevelNumber(double aqi){
		if(aqi <= 0){
			return 0;
		}
		if(aqi < 50){
			return 1;
		}else if(aqi < 100){
			return 2;
		}else if(aqi < 150){
			return 3;
		}else if(aqi < 200){
			return 4;
		}else if(aqi < 300){
			return 5;
		}else if(aqi > 300){
			return 6;
		}
		return 0;
	}
}