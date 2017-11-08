package com.zhaolong.wsn.dao.impl;

import com.zhaolong.wsn.dao.IPersonDao;

public class PersonDao implements IPersonDao{
	public String username;
	public String password;
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	public void setUsername(String name) {
		// TODO Auto-generated method stub
		this.username = name;
	}

	public void setPassword(String password) {
		// TODO Auto-generated method stub
		this.password = password;
	}

}
