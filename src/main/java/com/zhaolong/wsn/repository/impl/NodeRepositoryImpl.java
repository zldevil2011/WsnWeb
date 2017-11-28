package com.zhaolong.wsn.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhaolong.wsn.entity.Data;
import com.zhaolong.wsn.entity.Node;
import com.zhaolong.wsn.repository.NodeRepository;

public class NodeRepositoryImpl implements NodeRepository{

	@Autowired
    private SessionFactory sessionFactory;
	private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }
	
	public Node load(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Node get(Long id) {
		// TODO Auto-generated method stub
		return (Node) getCurrentSession().get(Node.class,id);
	}

	public List<Node> findAll() {
		// TODO Auto-generated method stub
		return (List<Node>) getCurrentSession().createQuery("from Node").list();
	}

	public void persist(Node entity) {
		// TODO Auto-generated method stub
		
	}

	public Long save(Node entity) {
		// TODO Auto-generated method stub
		return (Long)getCurrentSession().save(entity);
	}

	public void saveOrUpdate(Node entity) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}

}
