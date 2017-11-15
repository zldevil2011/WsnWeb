package com.zhaolong.wsn.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/*") 
public class Api {

	@RequestMapping(value = "data_list", method = RequestMethod.GET)
	public @ResponseBody List<Integer> dataList(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("index tempalte api");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		List<Integer> data = new ArrayList<Integer>();
		data.add(1);
		data.add(2);
		data.add(3);
		data.add(4);
		return data;
	}	
}