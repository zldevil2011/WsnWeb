package com.zhaolong.wsn.service;

import java.sql.Date;
import java.util.List;

import com.zhaolong.wsn.entity.Data;

public interface DataService {
	Long saveData(Data data);
	List<Data> dataList();
	List<Data> dataList(Date startDay, Date enDay);
	List<Data> dataList(Long nodeId);
	List<Data> dataList(Long nodeId, Date today);
	List<Data> dataList(Long nodeId, Date startDay, Date endDay);
}
