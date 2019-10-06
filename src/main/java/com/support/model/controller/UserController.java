package com.support.model.controller;

import com.support.core.controller.Session;
import com.support.model.entity.User;
import com.support.model.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class UserController {
	private UserService userService;

	public void SayHello(Map data){
		System.out.println(data.get("session").toString());
		Map<String,Object> session=Map.class.cast(data.get("session"));
		session.put("username","123");
		userService.hello();
		System.out.println(session.get("username"));
		session.remove("username");

	}

	public Map SayHello2(Map data){
		Map<String,Object>result=new HashMap<>();
		Map session= (Map) data.get("session");
		result.put("Command", "LoginSuccess");
		result.put("LoginSuccess", "yuxudong");
		return result;
	}
	
	public void Regist(Map data) {
		String username = (String) data.get("username");
		String password = (String) data.get("password");
		User user = new User(username, password, 0.00);
		userService.regist(user);
	}
	
	public void Login(Map data) {
			String username = (String) data.get("username");
			String password = (String) data.get("password");
			Double account = (Double) data.get("account");
			User user = new User(username, password, account);
			userService.login(user);
	}
	
	public Map<String,Object> Deposit(Map data) {
		Map<String,Object> result=new HashMap<String,Object>();
		Map<String,Object> session = Map.class.cast(data.get("session"));
		if(session.get("username") == null) {
			result.put("Command", "NoLogin");
			result.put("NoLogin", null);
			return result;
		}else {
			return userService.deposit((String)data.get("money"));
		}
	}
	
	public Map<String, Object> Pay(Map data) {
		Map<String,Object> result=new HashMap<String,Object>();
		Map<String,Object> session = Map.class.cast(data.get("session"));
		if(session.get("username") == null) {
			result.put("Command", "NoLogin");
			result.put("NoLogin", null);
			return result;
		}else {
			return userService.pay((String)data.get("money"));
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
