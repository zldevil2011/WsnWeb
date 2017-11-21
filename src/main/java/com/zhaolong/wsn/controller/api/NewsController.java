package com.zhaolong.wsn.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhaolong.wsn.entity.News;
import com.zhaolong.wsn.service.NewsService;

@Controller
@RequestMapping(value = "/api/*") 
public class NewsController {

	@Autowired
	private NewsService newsService;
	
	@RequestMapping(value = "news_list", method = RequestMethod.GET)
	public @ResponseBody List<News> dataList(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("news_list api");
		List<News> data = new ArrayList<News>();
		data = newsService.newsList();
		System.out.println(data.size());
		return data;
	}
}
