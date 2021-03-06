package com.zhaolong.wsn.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhaolong.wsn.entity.Data;
import com.zhaolong.wsn.repository.DataRepository;
import com.zhaolong.wsn.service.DataService;

public class 	DataServiceImpl implements DataService{
	
	@Autowired
    private DataRepository dataRepository;
	
	public Long saveData(Data data) {
		// TODO Auto-generated method stub
		return dataRepository.save(data);
	}

	public void updateAqi(Long id, Data data) {
		dataRepository.updateAqi(id, data);
	}

	public List<Data> dataList() {
		// TODO Auto-generated method stub
		return dataRepository.findAll();
	}

	public List<Data> dataList(Long nodeId) {
		// TODO Auto-generated method stub
		return dataRepository.findAllById(nodeId);
	}

	public List<Data> dataList(Long nodeId, Date today) {
		// TODO Auto-generated method stub
		return dataRepository.findAllByIdTime(nodeId, today);
	}

	public List<Data> dataList(Long nodeId, Date startDay, Date endDay) {
		// TODO Auto-generated method stub
		return dataRepository.findAllByIdTimeRange(nodeId, startDay, endDay);
	}

	public List<Data> dataList(Date startDay, Date endDay) {
		// TODO Auto-generated method stub
		return dataRepository.findAllByTimeRange(startDay, endDay);
	}

	public Data latestData(Long nodeId) {
		// TODO Auto-generated method stub
		Data data = dataRepository.findLatestById(nodeId);
		return data;
	}

}
