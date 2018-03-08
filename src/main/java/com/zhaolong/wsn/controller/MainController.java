package com.zhaolong.wsn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String StartPage() {
		System.out.println("index template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "index";
	}

	@RequestMapping("/helloWorld")
	public String helloWorld(Model model) {
		String word0 = "Hello ";
		String word1 = "World!";
		//将数据添加到视图数据容器中
		model.addAttribute("word0",word0);
		model.addAttribute("word1",word1);
		return "Hello";
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		System.out.println("index template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "index";
	}

	@RequestMapping(value = "indexList", method = RequestMethod.GET)
	public String indexList() {
		System.out.println("index template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "indexList";
	}

	@RequestMapping(value = "realDataList", method = RequestMethod.GET)
	public String realDataList() {
		System.out.println("realDataList template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "realDataList";
	}

	@RequestMapping(value = "aqiInfo", method = RequestMethod.GET)
	public String aqiInfo() {
		System.out.println("aqiInfo template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "aqiInfo";
	}

	@RequestMapping(value = "dataWarning", method = RequestMethod.GET)
	public String dataWarning() {
		System.out.println("dataWarning template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "dataWarning";
	}

	@RequestMapping(value = "weather", method = RequestMethod.GET)
	public String weather() {
		System.out.println("weather template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "weather";
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
		System.out.println("node_list template");
		// 实际返回的是views/test.jsp ,spring-mvc.xml中配置过前后缀
		return "nodeList";
	}

	// 节点的详细数据
	@RequestMapping(value = "node_data/{node_id}", method = RequestMethod.GET)
	public String nodeData(@PathVariable("node_id") int node_id, ModelMap map) {
		map.put("node_id", node_id);
		return "nodeData";
	}

	// 节点的排名数据
	@RequestMapping(value = "node_rank", method = RequestMethod.GET)
	public String nodeRank() {
		return "nodeRank";
	}

	// 新闻列表数据
	@RequestMapping(value = "news_list", method = RequestMethod.GET)
	public String newsList() {
		return "newsList";
	}

	// 新增节点页面
	@RequestMapping(value = "node_add", method = RequestMethod.GET)
	public String nodeAdd() {
		return "nodeAdd";
	}
	// 数据渲染页面
	@RequestMapping(value = "airQuality", method = RequestMethod.GET)
	public String airQuality() {
		return "airQuality";
	}
	// 数据下载页面
	@RequestMapping(value = "dataExport", method = RequestMethod.GET)
	public String dataExport() {
		return "dataExport";
	}
}
