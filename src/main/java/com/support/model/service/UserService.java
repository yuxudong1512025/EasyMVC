package com.support.model.service;

import com.support.model.dao.UserDao;

public class UserService {
	private UserDao userDao;

	public void hello(){
		System.out.println("hello");
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
