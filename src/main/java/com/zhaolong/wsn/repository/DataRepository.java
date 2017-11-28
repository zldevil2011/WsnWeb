package com.zhaolong.wsn.repository;

import java.sql.Date;
import java.util.List;

import com.zhaolong.wsn.entity.Data;

public interface DataRepository extends DomainRepository<Data,Long>{
	Data findLatestById(Long nodeId);
	List<Data> findAllById(Long nodeId);
	List<Data> findAllByIdTime(Long nodeId, Date today);
	List<Data> findAllByIdTimeRange(Long nodeId, Date startDay, Date endDay);
	List<Data> findAllByTimeRange(Date startDay, Date endDay);
}
