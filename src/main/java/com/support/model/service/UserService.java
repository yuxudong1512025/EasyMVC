//package com.support.model.service;
//
//import java.util.HashMap;
//import java.util.Map;
//import com.support.model.dao.UserDao;
//import com.support.model.entity.*;
//
//public class UserService {
//	private UserDao userDao;
//
//	public void hello(){
//		System.out.println("hello");
//	}
//
//	public void setUserDao(UserDao userDao) {
//		this.userDao = userDao;
//	}
//
//	public Map<String,Object> regist(User user) {
//		Map<String,Object> result=new HashMap<String,Object>();
//		//User _user = userDao.findByUsername(user.getUserName());		
//		if(_user!=null) {
//			result.put("Command", "ErrorRegister");
//			result.put("ErrorRegister", null);
//			return result;		
//		}else {
//			userDao.add(user);
//			result.put("Command", "RegisterSuccess");
//			result.put("RegisterSuccess", user.getUserName());
//			return result;	
//		}
//	}
//	
//	public Map<String,Object> login(User form) {
//		Map<String,Object> result=new HashMap<String,Object>();
//		//User user = userDao.findByUsername(form.getUserName());				
//		if(user == null) {
//			result.put("Command", "NoRegister");
//			result.put("NoRegister", null);
//			return result;		
//		}	
//		if(!form.getPassword().equals(user.getPassword())) {			
//			result.put("Command", "ErrorPassword");
//			result.put("ErrorPassword", null);
//			return result;	
//		}else {			
//		result.put("Command", "LoginSuccess");
//		result.put("LoginSuccess", user.getUserName());
//		return result;	
//		}
//	}
//	
//	public Map<String, Object> deposit(String money) {
//		Map<String,Object> result=new HashMap<String,Object>();
//		result.put("Command", "DepositSuccess");
//		result.put("DepositSuccess", money);
//		return result;
//		
//	}
//	public Map<String,Object> pay(String money) {
//		Map<String,Object> result=new HashMap<String,Object>();
//		result.put("Command", "PaySuccess");
//		result.put("PaySuccess", money);
//		return result;
//	}
//
//}
