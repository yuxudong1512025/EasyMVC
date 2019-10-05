package com.support.core.adapter;

import com.publicgroup.factory.BeanFactory;
import com.publicgroup.util.Assert;
import com.support.core.config.TransDefinition;
import com.support.core.controller.Session;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
	public boolean userExist(String userName) {
		return Assert.isNotNull(session.getSession(userName));
	}

	@Override
	public void AddUser(String userName,String password) {
		session.setSession(userName,password);
	}

	@Override
	public void removeUser(String userName) {
		session.removeSession(userName);
	}

	public Object execute(TransDefinition transDefinition, Map data) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
		Class<?>actionClass=Class.forName(transDefinition.getTransUrl());
		Object action=beanFactory.getBean(actionClass.getSimpleName());
		Method method=null;
		try {
			method=actionClass.getMethod(transDefinition.getTransMethod(),actionClass);
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
