package com.zhaolong.wsn.controller.dataServer;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhaolong.wsn.entity.Data;
import com.zhaolong.wsn.service.DataService;
import com.zhaolong.wsn.service.NodeService;

@Controller
@RequestMapping(value = "/api/*") 
public class ReceiveServer {
	@Autowired
	private DataService dataService;
	
	@Autowired
	private NodeService nodeService;
	
	// 通过提供POST的接口接受来自其他服务器的数据
	@RequestMapping(value = "server_data_add", method = RequestMethod.POST)
	public @ResponseBody String serverDataAdd(HttpServletRequest request, HttpServletResponse response) {
		String dataDate=request.getParameter("dataDate");
	    String dataTime=request.getParameter("dataTime");
	    String nodeId=request.getParameter("nodeId");
	    String pm25=request.getParameter("pm25");
	    String pm10=request.getParameter("pm10");
	    String so2=request.getParameter("so2");
	    String no2=request.getParameter("no2");
	    String o3=request.getParameter("o3");
	    String co=request.getParameter("co");
	    String speed=request.getParameter("speed");
	    String direction=request.getParameter("direction");
	    String humidity=request.getParameter("humidity");
	    System.out.println(dataDate);
	    System.out.println(dataTime);
	    System.out.println(nodeId);
	    if(dataDate == null || dataTime == null || nodeId == null){
	    	return "error";
	    }
	    Data data = new Data();
	    try{
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	    	java.util.Date d = null; 
	    	try { 
	    		d = format.parse(dataDate); 
	    	} catch (Exception e) { 
	    	   e.printStackTrace(); 
	    	} 
	    	java.sql.Date date = new java.sql.Date(d.getTime()); 
	    	data.setDataDate(date);
	    }catch (Exception e) {
			// TODO: handle exception
		}
	    try{
	    	SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss"); 
	    	java.util.Date d = null; 
	    	try { 
	    		d = format.parse(dataTime); 
	    	} catch (Exception e) { 
	    		e.printStackTrace(); 
	    	} 
	    	java.sql.Time time = new java.sql.Time(d.getTime());
	    	data.setDataTime(time);
	    }catch (Exception e) {
			// TODO: handle exception
		}
	    try{
	    	data.setNodeId(Long.parseLong(nodeId));
	    }catch (Exception e) {
			// TODO: handle exception
		}
	    try{
	    	data.setPm25(Double.parseDouble(pm25));
	    }catch (Exception e) {
			// TODO: handle exception
		}
	    try{
	    	data.setPm10(Double.parseDouble(pm10));
	    }catch (Exception e) {
			// TODO: handle exception
		}
	    try {
	    	data.setSo2(Double.parseDouble(so2));
		} catch (Exception e) {
			// TODO: handle exception
		}
	    try {
	    	data.setNo2(Double.parseDouble(no2));
		} catch (Exception e) {
			// TODO: handle exception
		}
	    try {
	    	data.setO3(Double.parseDouble(o3));
		} catch (Exception e) {
			// TODO: handle exception
		}
	    try {
	    	data.setCo(Double.parseDouble(co));
		} catch (Exception e) {
			// TODO: handle exception
		}
	    try {
	    	data.setWindSpeed(Double.parseDouble(speed));
		} catch (Exception e) {
			// TODO: handle exception
		}
	    try {
	    	data.setWindyDirection(Double.parseDouble(direction));
		} catch (Exception e) {
			// TODO: handle exception
		}
	    try {
	    	data.setAirHumidity(Double.parseDouble(humidity));
		} catch (Exception e) {
			// TODO: handle exception
		}
	    System.out.println(data.getDataDate());
		return "success";
	}
}
