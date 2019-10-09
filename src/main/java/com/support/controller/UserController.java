package com.support.controller;

import com.publicgroup.util.Assert;
import com.support.core.resolver.StringViewResolver;
import com.support.model.entity.User;
import com.support.model.service.UserService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class UserController {
	private UserService userService;

//	public void sayHello(Map data) {
//		Map<String, Object> session = Map.class.cast(data.get("session"));
//		session.put("username", "123");
//		userService.hello();
//		session.remove("username");
//	}
//
//	public Map sayHello2(Map data) {
//		Map<String, Object> result = new HashMap<>(64);
//		result.put("Command",StringViewResolver.LOGINSUCCESS);
//		result.put("LoginSuccess", "yuxudong");
//		return result;
//	}

	public Map register(Map data) {
		String username = (String) data.get("username");
		String password = (String) data.get("password");
		if(!Assert.isNotNull(username)||!Assert.isNotNull(password)){
			Map<String,Object> result=new HashMap<>();
			result.put("Command", StringViewResolver.EMPTYPNAMEORPASSWORD);
			result.put(StringViewResolver.EMPTYPNAMEORPASSWORD.getKey(), null);
			return result;
		}
		User user = new User(username, password, new BigDecimal("0.00"));
		Map result = userService.register(user);
		if (result.get("Command").equals(StringViewResolver.REGISTERSUCCESS)) {
			Map<String, Object> session = (Map) data.get("session");
			session.remove("user");
			session.put("user", user);
		}
		return result;
	}

	public Map login(Map data) {
		String username = (String) data.get("username");
		String password = (String) data.get("password");
		if(!Assert.isNotNull(username)||!Assert.isNotNull(password)){
			Map<String,Object> result=new HashMap<>();
			result.put("Command", StringViewResolver.EMPTYPNAMEORPASSWORD);
			result.put(StringViewResolver.EMPTYPNAMEORPASSWORD.getKey(), null);
			return result;
		}

		BigDecimal account = (BigDecimal) data.get("account");
		User user = new User(username, password, account);
		Map<String, Object> session = (Map) data.get("session");

		if (Assert.isNotNull(session.get("user"))) {
			session.remove("user");
		}
		User userdata = userService.login(user);

		Map<String, Object> result = new HashMap<>(64);
		if (!Assert.isNotNull(userdata)) {
			result.put("Command", StringViewResolver.NOREGISTER);
			result.put("NoRegister", username);
			return result;
		}
		if (!user.getPassword().equals(userdata.getPassword())) {
			result.put("Command", StringViewResolver.ERRORPASSWORD);
			result.put("ErrorPassword", null);
			return result;
		} else {
			result.put("Command", StringViewResolver.LOGINSUCCESS);
			result.put("LoginSuccess", user.getUserName());
			session.put("user", userdata);
			return result;
		}
	}

	public Map<String, Object> deposit(Map data) {
		Map<String, Object> result = new HashMap<>(64);
		Map<String, Object> session = Map.class.cast(data.get("session"));
		if (session.get("user") == null) {
			result.put("Command", StringViewResolver.NOLOGIN);
			result.put("NoLogin", null);
			return result;
		} else {
			return userService.deposit((String) data.get("money"), (User) session.get("user"));
		}
	}

	public Map<String, Object> pay(Map data) {
		Map<String, Object> result = new HashMap<>(64);
		Map<String, Object> session = Map.class.cast(data.get("session"));
		if (!Assert.isNotNull(session.get("user"))) {
			result.put("Command",StringViewResolver.NOLOGIN);
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
