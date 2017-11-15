package com.zhaolong.wsn.service;

import java.util.List;

import com.zhaolong.wsn.entity.Data;

public interface DataService {
	Long saveData(Data data);
	List<Data> dataList();
}
