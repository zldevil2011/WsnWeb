package com.zhaolong.wsn.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhaolong.wsn.entity.News;
import com.zhaolong.wsn.repository.NewsRepository;

public class NewsRepositoryImpl implements NewsRepository {

	@Autowired
    private SessionFactory sessionFactory;
	private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }
	
	public News load(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public News get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<News> findAll() {
		// TODO Auto-generated method stub
		return (List<News>) getCurrentSession().createQuery("from News").list();
	}

	public void persist(News entity) {
		// TODO Auto-generated method stub
		
	}

	public Long save(News entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveOrUpdate(News entity) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}

}
