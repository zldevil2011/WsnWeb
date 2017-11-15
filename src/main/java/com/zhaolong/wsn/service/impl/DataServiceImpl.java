package com.zhaolong.wsn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhaolong.wsn.entity.Data;
import com.zhaolong.wsn.repository.DataRepository;
import com.zhaolong.wsn.service.DataService;

public class DataServiceImpl implements DataService{
	
	@Autowired
    private DataRepository dataRepository;
	
	public Long saveData(Data data) {
		// TODO Auto-generated method stub
		return dataRepository.save(data);
	}

	public List<Data> dataList() {
		// TODO Auto-generated method stub
		return dataRepository.findAll();
	}

}
