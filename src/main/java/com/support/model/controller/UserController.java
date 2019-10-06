package com.support.model.controller;

import com.publicgroup.util.Assert;
import com.support.core.controller.Session;
import com.support.model.entity.User;
import com.support.model.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class UserController {
	private UserService userService;

	public void SayHello(Map data) {
		System.out.println(data.get("session").toString());
		Map<String, Object> session = Map.class.cast(data.get("session"));
		session.put("username", "123");
		userService.hello();
		System.out.println(session.get("username"));
		session.remove("username");

	}

	public Map SayHello2(Map data) {
		Map<String, Object> result = new HashMap<>();
		Map session = (Map) data.get("session");
		result.put("Command", "LoginSuccess");
		result.put("LoginSuccess", "yuxudong");
		return result;
	}

	public Map Regist(Map data) {
		String username = (String) data.get("username");
		String password = (String) data.get("password");
		User user = new User(username, password, 0.00);
		Map result = userService.regist(user);
		if (result.get("Command").toString().equals("RegisterSuccess")) {
			Map<String, Object> session = (Map) data.get("session");
			session.remove("user");
			session.put("user", user);
		}
		return result;
	}

	public Map Login(Map data) {
		String username = (String) data.get("username");
		String password = (String) data.get("password");
		Double account = (Double) data.get("account");
		User user = new User(username, password, account);
		Map<String, Object> session = (Map) data.get("session");
		if (Assert.isNotNull(session.get("user"))) {
			session.remove("user");
		}
		User _user = userService.login(user);
		Map<String, Object> result = new HashMap<String, Object>();
		if (_user == null) {
			result.put("Command", "NoRegister");
			result.put("NoRegister", _user.getUserName());
			return result;
		}
		if (!user.getPassword().equals(_user.getPassword())) {
			result.put("Command", "ErrorPassword");
			result.put("ErrorPassword", null);
			return result;
		} else {
			result.put("Command", "LoginSuccess");
			result.put("LoginSuccess", user.getUserName());
			session.put("user", _user);
			return result;
		}
	}

	public Map<String, Object> Deposit(Map data) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> session = Map.class.cast(data.get("session"));
		if (session.get("user") == null) {
			result.put("Command", "NoLogin");
			result.put("NoLogin", null);
			return result;
		} else {
			return userService.deposit((String) data.get("money"), (User) session.get("user"));
		}
	}

	public Map<String, Object> Pay(Map data) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> session = Map.class.cast(data.get("session"));
		if (!Assert.isNotNull(session.get("user"))) {
			result.put("Command", "Nologin");
			result.put("Nologin", null);
			return result;
		} else {
			return userService.pay((String) data.get("money"), (User) session.get("user"));
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
