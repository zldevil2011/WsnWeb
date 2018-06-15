package com.zhaolong.wsn.repository;

import com.zhaolong.wsn.entity.Person;

public interface PersonRepository extends DomainRepository<Person,Long>{
	public Person login(String username, String password);
	public Person getPersonByUsername(String username);
}
