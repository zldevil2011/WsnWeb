package com.zhaolong.wsn.repository.impl;

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
		return (Data)getCurrentSession().load(Data.class,id);
	}

	public Data get(Long id) {
		// TODO Auto-generated method stub
		return (Data)getCurrentSession().get(Data.class,id);
	}

	public List<Data> findAll() {
		// TODO Auto-generated method stub
		return (List<Data>) getCurrentSession().createQuery("from Data").list();
	}

	public void persist(Data entity) {
		// TODO Auto-generated method stub
		getCurrentSession().persist(entity);
	}

	public Long save(Data entity) {
		// TODO Auto-generated method stub
		return (Long)getCurrentSession().save(entity);
	}

	public void saveOrUpdate(Data entity) {
		// TODO Auto-generated method stub
		getCurrentSession().saveOrUpdate(entity);
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		Data data = load(id);
        getCurrentSession().delete(data);
	}

	public void flush() {
		// TODO Auto-generated method stub
		getCurrentSession().flush();
	}

}
