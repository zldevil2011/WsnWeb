package com.zhaolong.wsn.controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.zhaolong.wsn.service.DataService;

@Controller
@RequestMapping(value = "/api/*") 
public class DataController {
	
	@Autowired
	private DataService dataService;
	
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
}
@JsonIgnoreProperties
class RetData{
	double value;
	int year;
	int month;
	int day;
	int hour;
	int minute;
	int second;
}