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
		return null;
	}

	public List<Information> findAll() {
		// TODO Auto-generated method stub
		return (List<Information>) getCurrentSession().createQuery("from Information").list();
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
		
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}

}
