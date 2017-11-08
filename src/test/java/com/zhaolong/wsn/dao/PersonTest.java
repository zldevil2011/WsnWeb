package com.zhaolong.wsn.dao;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Before;
import org.junit.Test;
import com.zhaolong.wsn.dao.impl.PersonDao;

public class PersonTest {
private BeanFactory factory = null;
	@Before  
    public void before() {  
        factory = new ClassPathXmlApplicationContext("applicationContext.xml");  
    }  
    @Test  
    public void testSpring() { 
    	System.out.println("I am testing");
    	PersonDao personDao = new PersonDao();
    	personDao.setUsername("zhaolong123");
    	System.out.println(personDao.getUsername());
    } 
}
