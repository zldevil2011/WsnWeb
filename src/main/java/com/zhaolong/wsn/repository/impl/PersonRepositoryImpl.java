package com.zhaolong.wsn.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhaolong.wsn.entity.Person;
import com.zhaolong.wsn.repository.PersonRepository;

public class PersonRepositoryImpl implements PersonRepository {
	
	@Autowired
    private SessionFactory sessionFactory;
	private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }
	public Person load(Long id) {
		// TODO Auto-generated method stub
		return (Person)getCurrentSession().load(Person.class,id);
	}

	public Person login(String username, String password) {
		// TODO Auto-generated method stub
		return (Person) getCurrentSession().createQuery("from Person where username=? and password=?").setParameter(0, username).setParameter(1, password).uniqueResult();
	}
	public Person getPersonByUsername(String username) {
		return (Person) getCurrentSession().createQuery("from Person where username=?").setParameter(0, username).uniqueResult();
	}
	public Person get(Long id) {
		// TODO Auto-generated method stub
		return (Person)getCurrentSession().get(Person.class,id);
	}

	public List<Person> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void persist(Person entity) {
		// TODO Auto-generated method stub
		getCurrentSession().persist(entity);
	}

	public Long save(Person entity) {
		// TODO Auto-generated method stub
		return (Long)getCurrentSession().save(entity);
	}

	public void saveOrUpdate(Person entity) {
		// TODO Auto-generated method stub
		getCurrentSession().saveOrUpdate(entity);
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		Person person = load(id);
        getCurrentSession().delete(person);
	}

	public void flush() {
		// TODO Auto-generated method stub
		getCurrentSession().flush();
	}

}
