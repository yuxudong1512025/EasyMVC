package com.support.model.service;

import java.util.HashMap;
import java.util.Map;

import com.publicgroup.util.Assert;
import com.publicgroup.util.log.LogFactory;
import com.support.core.resolver.StringViewResolver;
import com.support.model.dao.UserDao;
import com.support.model.entity.*;

public class UserService {
	private UserDao userDao;

	private Map<String, Object> result = new HashMap<>(64);

	public void hello() {
		LogFactory.getGlobalLog().info("hello");
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public Map<String, Object> regist(User user) {
		result.clear();
		User userdata = userDao.findByUsername(user.getUserName());
		if (Assert.isNotNull(userdata)) {
			result.put("Command", StringViewResolver.ERRORREGISTER);
			result.put("ErrorRegister", null);
			return result;
		} else {
			userDao.add(user);
			result.put("Command", StringViewResolver.REGISTERSUCCESS);
			result.put("RegisterSuccess", user.getUserName());
			return result;
		}
	}

	public User login(User form) {
		return userDao.findByUsername(form.getUserName());
	}

	public Map<String, Object> deposit(String money, User user) {
		result.clear();
		user.setAccount(user.getAccount() + Double.valueOf(money));
		userDao.update(user);

		result.put("Command", StringViewResolver.DEPOSITSUCCESS);
		result.put("DepositSuccess", money);
		return result;

	}

	public Map<String, Object> pay(String money, User user) {
		result.clear();
		if (user.getAccount() < Double.valueOf(money)) {
			result.put("Command", StringViewResolver.PAYFAIL);
			result.put("PayFail", money);
		} else {
			user.setAccount(user.getAccount() - Double.valueOf(money));
			userDao.update(user);
			result.put("Command", StringViewResolver.PAYSUCCESS);
			result.put("PaySuccess", money);
		}
		return result;
	}

}
