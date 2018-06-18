package com.zhaolong.wsn.service;

import com.zhaolong.wsn.entity.Person;

public interface PersonService {
	Long register(Person person);
	Person login(String username, String password);
	Person getPerson(String username);
	Person getPerson(Long userId);
	void updatePerson(Person person);
}
