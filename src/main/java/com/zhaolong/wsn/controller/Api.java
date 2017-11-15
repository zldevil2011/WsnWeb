package com.zhaolong.wsn.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zhaolong.wsn.entity.Data;
import com.zhaolong.wsn.repository.DataRepository;
import com.zhaolong.wsn.service.DataService;

@Controller
@RequestMapping(value = "/api/*") 
public class Api {
	
	@Autowired
	private DataService dataService;
	
	@RequestMapping(value = "data_list", method = RequestMethod.GET)
	public @ResponseBody List<Data> dataList(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("index tempalte api");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		List<Data> data = new ArrayList<Data>();
		data = dataService.dataList();
		System.out.println(data.size());
		return data;
	}
	@RequestMapping(value = "data_save", method = RequestMethod.GET)
	public void dataSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Data d = new Data();
		d.setCo("12.3");
		d.setDireciton("NS");
		d.setNo2("33.33");
		dataService.saveData(d);
		response.setStatus(200);
		PrintWriter pWriter = response.getWriter();
		pWriter.println("success");
	}
}