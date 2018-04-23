package com.zhaolong.wsn.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhaolong.wsn.entity.Information;
import com.zhaolong.wsn.repository.InformationRepository;

public class InformationRepositoryImpl implements InformationRepository {

	@Autowired
    private SessionFactory sessionFactory;
	private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }
	
	public Information load(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Information get(Long id) {
		// TODO Auto-generated method stub
		Session currentSession = getCurrentSession();
		Information res = (Information) currentSession.get(Information.class,id);
		currentSession.close();
		return res;
	}

	public List<Information> findAll() {
		// TODO Auto-generated method stub
		Session currentSession = getCurrentSession();
		List<Information> res = (List<Information>)currentSession.createQuery("from Information").list();
		currentSession.close();
		return res;
	}

	public void persist(Information entity) {
		// TODO Auto-generated method stub
		
	}

	public Long save(Information entity) {
		// TODO Auto-generated method stub
		return (Long)getCurrentSession().save(entity);
	}

	public void saveOrUpdate(Information entity) {
		// TODO Auto-generated method stub
		System.out.println("entity.setReadCoun");
		System.out.println(entity.getReadCount());
		System.out.println(entity.getId());

		Session currentSession = getCurrentSession();
		currentSession.saveOrUpdate(entity);
		currentSession.flush();
		currentSession.close();
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}

}
