package com.support.core.adapter;

import com.publicgroup.factory.BeanFactory;
import com.publicgroup.util.Assert;
import com.support.core.config.TransDefinition;
import com.support.core.controller.Session;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 */
public class StringHandlerAdapter implements authentication {

	private Session session;

	private BeanFactory beanFactory;

	public void setSession(Session session) {
		this.session = session;
	}

	public void setBeanFactory(BeanFactory beanFactory){
		this.beanFactory=beanFactory;
	}

	@Override
	public boolean userExist() {
		return Assert.isNotNull(session.getSession("username"));
	}

	@Override
	public void AddUser(String userName,String password) {
		session.setSession("userName",userName);
		session.setSession("userPassword",password);
	}

	@Override
	public void removeUser() {
		session.removeSession("userName");
		session.removeSession("userPassword");
	}

	public boolean checkAuth(){
		return userExist();
	}

	public Object doHandler(TransDefinition transDefinition, Map data) throws IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		if(transDefinition.containRule("checkAuth")){
			if(checkAuth()){
				return execute(transDefinition,data);
			}else{
				Map Errorresult=new HashMap<String,Object>();
				Errorresult.put("Command","Nologin");
				Errorresult.put("Nologin",null);
				return Errorresult;
			}
		}
		return execute(transDefinition,data);
	}


	public Object execute(TransDefinition transDefinition, Map data) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
		System.out.println(transDefinition.getTransUrl());
		Class<?>actionClass=Class.forName(transDefinition.getTransUrl());
		System.out.println(actionClass.getSimpleName());
		Object action=beanFactory.getBean(actionClass.getSimpleName());
		Method method=null;
		try {
			method=actionClass.getDeclaredMethod(transDefinition.getTransMethod());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		Object[]arg=new Object[method.getParameterCount()];
		Parameter[]list=method.getParameters();
		int count=0;
		for (Parameter parameter:list){
			Class<?>pType=parameter.getType();
			String pName=parameter.getName();
			arg[count++]=pType.cast(data.get(pName));
		}

		Object returnData=method.invoke(action,arg);

		return method.getReturnType().cast(returnData);
	}
}
