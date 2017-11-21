package com.zhaolong.wsn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhaolong.wsn.entity.News;
import com.zhaolong.wsn.repository.NewsRepository;
import com.zhaolong.wsn.service.NewsService;

public class NewsServiceImpl implements NewsService {
	
	@Autowired
    private NewsRepository newsRepository;
	
	public List<News> newsList() {
		// TODO Auto-generated method stub
		return newsRepository.findAll();
	}

}
