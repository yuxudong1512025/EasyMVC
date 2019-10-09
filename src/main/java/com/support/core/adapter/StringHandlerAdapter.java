package com.support.core.adapter;

import com.publicgroup.factory.BeanFactory;
import com.publicgroup.util.log.LogFactory;
import com.support.core.config.TransDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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


	//执行方法
	public Object execute(TransDefinition transDefinition, Map data) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		logger.info("TransURL "+transDefinition.getTransUrl());
		//根据路径名生成class对象
		Class<?> actionClass = Class.forName(transDefinition.getTransUrl());

		//首字母大写转小写
		String beanName = actionClass.getSimpleName().substring(0, 1).toLowerCase() + actionClass.getSimpleName().substring(1);
		logger.info("beanName "+beanName);
		//根据beanName找到userController对象
		Object action = beanFactory.getBean(beanName);

		logger.info("methodName "+transDefinition.getTransMethod());
		//拿到方法
		Method method = actionClass.getDeclaredMethod(transDefinition.getTransMethod(), Map.class);
		//调用方法
		Object returnData = method.invoke(action, data);

		return method.getReturnType().cast(returnData);

	}
}
