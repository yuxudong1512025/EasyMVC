package com.support.model.controller;

import com.sun.javaws.security.AppContextUtil;
import com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages_es;
import com.support.core.controller.Session;
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
		result.put("Command","LoginSuccess");
		result.put("LoginSuccess","yuxudong");
		return result;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
