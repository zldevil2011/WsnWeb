package com.zhaolong.wsn.util;

import com.zhaolong.wsn.entity.Data;
import com.zhaolong.wsn.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Aqi {
	private String[] pList = {"so2", "no2", "pm10", "co", "o3", "pm25"};
	private double[] transform_factor = {2949.276785714286, 2054.017857142857, 1, 1.2504464285714287, 2142.7767857142856, 1};
	public double AQI_24 = 0;
	public double AQI_1 = 0;
	private double[][][] fix_value_24 = {
			{{0.0,1.0},{50,1.0/2},{150,2.0/13},{475,2.0/13},{800,1.0/8},{1600,1.0/5},{2100, 5.0/26}},
			{{0.0,5.0/4},{40,5.0/4},{80,1.0/2},{180,1.0/2},{280,10.0/57},{565,20.0/37},{750, 10.0/19}},
			{{0.0,1.0},{50,1.0/2},{150,1.0/2},{250,1.0/2},{350,10.0/7},{420,5.0/4},{500, 1.0}},
			{{0.0,25.0},{2,25.0},{4,5.0},{14,5.0},{24, 25.0/3},{36,25.0/3},{48, 25.0/3}},
			{{0.0,1.0/2},{100,5.0/6},{160,10.0/11},{215,1.0},{265,20.0/107},{800,1.0/2},{1000, 1.0/2}},
			{{0.0,10.0/7},{35,5.0/4},{75,5.0/4},{115,10.0/7},{150,1.0},{250,1.0},{350, 2.0/3}},
		};
	private double[][][] fix_value_1 = {
			{{0.0,1.0/3},{150,1.0/7},{500,1.0/3},{650,1.0/3},{800,1.0/8},{1600,1.0/5},{2100, 5.0/26}},
			{{0.0,1.0/2},{100,1.0/2},{200,1.0/10},{700,1.0/10},{1200,5.0/57},{2340,2.0/15},{3090, 2.0/15}},
			{{0.0,1.0},{50,1.0/2},{150,1.0/2},{250,1.0/2},{350,10.0/7},{420,5.0/4},{500, 1.0}},
			{{0.0,10.0},{5,10.0},{10,2.0},{35,2.0},{60, 10.0/3},{90,10.0/3},{120, 10.0/3}},
			{{0.0,5.0/16},{160,5.0/4},{200,1.0/2},{300,1.0/2},{400,1.0/4},{800,1.0/2},{1000, 1.0/2}},
			{{0.0,10.0/7},{35,5.0/4},{75,5.0/4},{115,10.0/7},{150,1.0},{250,1.0},{350, 2.0/3}},
		};
	private double[] startPoint = {400,300,200,150,100,50,0};
	// 计算小时级别的每个特征的对应的值
	public void getHourItemAqi(double[] data){
		// data示例：[30,10,112,4,12,50] 分别代表该数据记录的"so2", "no2", "pm10", "co", "o3", "pm25"
		int dLen = data.length;
		for(int i = 0; i < dLen; ++i){
			if(data[i] <= 0){
				data[i] = 0;
			}else {
				try {
					data[i] = data[i] / this.transform_factor[i];
				} catch (Exception e) {
					data[i] = 0;
				}
			}
		}
		List<Double> HourItemAqiList = new ArrayList<Double>();
		for(int i = 0; i < dLen; ++i){
			double val = data[i];
			if(val > 0) {
				double[][] tmpData = this.fix_value_1[i];
				int len = tmpData.length;
				int cnt = -1;
				for (int j = len - 1; j >= 0; --j) {
					cnt += 1;
					if (val >= tmpData[j][0]) {
						double tmp = (val - tmpData[j][0]) * tmpData[j][1] + this.startPoint[cnt];
						HourItemAqiList.add(tmp);
						break;
					}
				}
			}else{
				HourItemAqiList.add(-1.0);
			}
		}
		double HourAqi = -1;
		for(int i = 0; i < HourItemAqiList.size(); ++i){
			if(HourItemAqiList.get(i) > HourAqi){
				HourAqi = HourItemAqiList.get(i);
			}
		}
		this.AQI_1 = HourAqi;
	}
	// 计算每日的每个特征的对应的值
	public void getDayItemAqi(double[] data){
		// data示例：[30,10,112,4,12,50] 分别代表该数据记录的"so2", "no2", "pm10", "co", "o3", "pm25"
		int dLen = data.length;
		for(int i = 0; i < dLen; ++i){
			if(data[i] <= 0){
				data[i] = 0;
			}else {
				try {
					data[i] = data[i] / this.transform_factor[i];
				} catch (Exception e) {
					data[i] = 0;
				}
			}
		}
		List<Double> DayItemAqiList = new ArrayList<Double>();
		for(int i = 0; i < dLen; ++i){
			double val = data[i];
			double[][] tmpData = this.fix_value_24[i];
			int len = tmpData.length;
			int cnt = -1;
			for(int j = len - 1; j >= 0; --j){
				cnt += 1;
				if(val >= tmpData[j][0]){
					double tmp = (val - tmpData[j][0]) * tmpData[j][1] + this.startPoint[cnt];
					DayItemAqiList.add(tmp);
					break;
				}
			}
		}
		double Aqi = -1;
		for(int i = 0; i < DayItemAqiList.size(); ++i){
			if(DayItemAqiList.get(i) > Aqi){
				Aqi = DayItemAqiList.get(i);
			}
		}
		this.AQI_24 = Aqi;
	}
	public void output(List<Double> tData){
		int dLen = tData.size();
		for(int i = 0; i < dLen; ++i){
			System.out.print(tData.get(i) + " ");
		}
		System.out.println("\n");
	}
	public static void main(String[] args) {
		Aqi aqi = new Aqi();
		double[] data = {32, 53, 294, 2, 33, 158};
		aqi.getHourItemAqi(data);
		System.out.println(aqi.AQI_1);
	}
}
