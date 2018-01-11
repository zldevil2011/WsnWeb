package com.zhaolong.wsn.repository.impl;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhaolong.wsn.entity.Data;
import com.zhaolong.wsn.entity.Person;
import com.zhaolong.wsn.repository.DataRepository;

public class DataRepositoryImpl implements DataRepository{
	@Autowired
    private SessionFactory sessionFactory;
	private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }
	public Data load(Long id) {
		// TODO Auto-generated method stub
		Session currentSession = getCurrentSession();
		Data res = (Data)currentSession.load(Data.class,id);
		currentSession.close();
		return res;
	}

	public Data get(Long id) {
		// TODO Auto-generated method stub
		Session currentSession = getCurrentSession();
		Data res = (Data)currentSession.get(Data.class,id);
		currentSession.close();
		return res;
	}

	public List<Data> findAll() {
		// TODO Auto-generated method stub
		Session currentSession = getCurrentSession();
		List<Data> res = (List<Data>) currentSession.createQuery("from Data").list();
		currentSession.close();
		return res;
	}
	
	public List<Data> findAllById(Long nodeId) {
		// 通过节点ID获取该节点的数据
		Session currentSession = getCurrentSession();
		List<Data> res = (List<Data>) currentSession.createQuery("from Data where nodeId = " + nodeId).list();
		currentSession.close();
		return res;
	}
	public List<Data> findAllByIdTime(Long nodeId, Date today) {
		// TODO Auto-generated method stub
		Session currentSession = getCurrentSession();
		List<Data> res = (List<Data>) currentSession.createQuery("from Data where nodeId = " + nodeId + " and dataDate ='" + today+"'").list();
		currentSession.close();
		return res;
	}
	public List<Data> findAllByIdTimeRange(Long nodeId, Date startDay, Date endDay) {
		// TODO Auto-generated method stub
		Session currentSession = getCurrentSession();
		List<Data> res = (List<Data>)currentSession.createQuery("from Data where nodeId = " + nodeId + " and dataDate >='" + startDay + "' and dataDate < '" + endDay+"'").list();
		currentSession.close();
		return res;
	}
	public List<Data> findAllByTimeRange(Date startDay, Date endDay) {
		// TODO Auto-generated method stub
		Session currentSession = getCurrentSession();
		List<Data> res = (List<Data>) currentSession.createQuery("from Data where dataDate >='" + startDay + "' and dataDate < '" + endDay+"'").list();
		currentSession.close();
		return res;
	}
	public void persist(Data entity) {
		// TODO Auto-generated method stub
		Session currentSession = getCurrentSession();
		currentSession.persist(entity);
		currentSession.close();
	}

	public Long save(Data entity) {
		// TODO Auto-generated method stub
		Session currentSession = getCurrentSession();
		Long res = (Long)currentSession.save(entity);
		currentSession.close();
		return res;
	}

	public void saveOrUpdate(Data entity) {
		// TODO Auto-generated method stub
		Session currentSession = getCurrentSession();
		currentSession.saveOrUpdate(entity);
		currentSession.close();
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		Data data = load(id);
		Session currentSession = getCurrentSession();
		currentSession.delete(data);
		currentSession.close();
	}

	public void flush() {
		// TODO Auto-generated method stub
		getCurrentSession().flush();
	}
	public Data findLatestById(Long nodeId) {
		// TODO Auto-generated method stub
		try{
			Session currentSession = getCurrentSession();

			Data res = (Data)currentSession.createQuery("from Data where nodeId = " + nodeId + " order by dataDate desc").setMaxResults(10).list().get(0);
			currentSession.close();
			return res;
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
