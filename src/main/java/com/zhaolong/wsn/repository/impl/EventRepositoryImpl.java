package com.zhaolong.wsn.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhaolong.wsn.entity.Event;
import com.zhaolong.wsn.repository.EventRepository;

public class EventRepositoryImpl implements EventRepository{

	@Autowired
    private SessionFactory sessionFactory;
	private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }
	
	public Event load(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Event get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Event> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void persist(Event entity) {
		// TODO Auto-generated method stub
		
	}

	public Long save(Event entity) {
		// TODO Auto-generated method stub
		return (Long)getCurrentSession().save(entity);
	}

	public void saveOrUpdate(Event entity) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}

}
