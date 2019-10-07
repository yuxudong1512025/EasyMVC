package com.support.core.adapter;

import com.publicgroup.factory.BeanFactory;
import com.publicgroup.util.Assert;
import com.publicgroup.util.log.LogFactory;
import com.support.core.config.TransDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author yuxudong
 */
public class StringHandlerAdapter  {

	private static final Logger logger= LogFactory.getGlobalLog();

	private BeanFactory beanFactory;

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}


	public Object doHandler(TransDefinition transDefinition, Map data) throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
		return execute(transDefinition, data);
	}


	public Object execute(TransDefinition transDefinition, Map data) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		logger.info(transDefinition.getTransUrl());
		Class<?> actionClass = Class.forName(transDefinition.getTransUrl());
		String beanName = actionClass.getSimpleName().substring(0, 1).toLowerCase() + actionClass.getSimpleName().substring(1);
		logger.info(beanName);
		Object action = beanFactory.getBean(beanName);
		logger.info(transDefinition.getTransMethod());
		Method method = actionClass.getDeclaredMethod(transDefinition.getTransMethod(), Map.class);
		Object returnData = method.invoke(action, data);
		return method.getReturnType().cast(returnData);

	}
}
