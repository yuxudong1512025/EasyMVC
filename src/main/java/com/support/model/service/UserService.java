package com.support.model.service;

import java.util.HashMap;
import java.util.Map;

import com.support.model.dao.UserDao;
import com.support.model.entity.*;

public class UserService {
	private UserDao userDao;

	public void hello() {
		System.out.println("hello");
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public Map<String, Object> regist(User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		User _user = userDao.findByUsername(user.getUserName());
		if (_user != null) {
			result.put("Command", "ErrorRegister");
			result.put("ErrorRegister", null);
			return result;
		} else {
			userDao.add(user);
			result.put("Command", "RegisterSuccess");
			result.put("RegisterSuccess", user.getUserName());
			return result;
		}
	}

	public User login(User form) {
		User user = userDao.findByUsername(form.getUserName());
		return user;
	}

	public Map<String, Object> deposit(String money, User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		user.setAccount(user.getAccount() + Double.valueOf(money));
		userDao.update(user);

		result.put("Command", "DepositSuccess");
		result.put("DepositSuccess", money);
		return result;

	}

	public Map<String, Object> pay(String money, User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (user.getAccount() < Double.valueOf(money)) {
			result.put("Command", "PayFail");
			result.put("PayFail", money);
		} else {
			user.setAccount(user.getAccount() - Double.valueOf(money));
			userDao.update(user);
			result.put("Command", "PaySuccess");
			result.put("PaySuccess", money);
		}
		return result;
	}

}
