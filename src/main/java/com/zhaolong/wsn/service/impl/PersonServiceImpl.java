package com.zhaolong.wsn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaolong.wsn.entity.Person;
import com.zhaolong.wsn.repository.PersonRepository;
import com.zhaolong.wsn.service.PersonService;


@Service
public class PersonServiceImpl implements PersonService{
	@Autowired
    private PersonRepository personRepository;

	public Long savePerson() {
		// TODO Auto-generated method stub
		Person person = new Person();
        person.setUsername("XRog");
        person.setPhone("18381005946");
        person.setAddress("chenDu");
        person.setRemark("this is XRog");
        return personRepository.save(person);
	}


}
