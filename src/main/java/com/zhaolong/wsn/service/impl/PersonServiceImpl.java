package com.zhaolong.wsn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.zhaolong.wsn.entity.Person;
import com.zhaolong.wsn.repository.PersonRepository;
import com.zhaolong.wsn.service.PersonService;


@Service
public class PersonServiceImpl implements PersonService{
	@Autowired
    private PersonRepository personRepository;

	public Long register(Person person) {
		// TODO Auto-generated method stub
		System.out.println("zhaolong_debug");
		System.out.println(person.getUsername());
        return personRepository.save(person);
	}

	public Person login(String username, String password) {
		// TODO Auto-generated method stub
		try {
			return personRepository.login(username, password);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}


}
