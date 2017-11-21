package com.zhaolong.wsn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		System.out.println("index tempalte");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "index";
	}	
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register() {
		System.out.println("user register mapping!");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "register";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		System.out.println("user login mapping!");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "login";
	}
	
	@RequestMapping(value = "node_list", method = RequestMethod.GET)
	public String nodeList() {
		System.out.println("node_list tempalte");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "nodeList";
	}
	
	// 节点的详细数据
	@RequestMapping(value = "node_data", method = RequestMethod.GET)
	public String nodeData() {
		return "nodeData";
	}
	
	// 节点的排名数据
	@RequestMapping(value = "node_rank", method = RequestMethod.GET)
	public String node() {
		return "nodeRank";
	}
}
