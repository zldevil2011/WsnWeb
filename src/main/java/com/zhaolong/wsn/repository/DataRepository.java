package com.zhaolong.wsn.repository;

import java.sql.Date;
import java.util.List;

import com.zhaolong.wsn.entity.Data;

public interface DataRepository extends DomainRepository<Data,Long>{
	List<Data> findAllById(Long nodeId);
	List<Data> findAllByIdTime(Long nodeId, Date today);
	List<Data> findAllByTimeRange(Long nodeId, Date startDay, Date endDay);
}
