package com.support.core.config;

import com.publicgroup.util.log.LogFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultTransDefinition implements TransDefinition {
	private static final Logger logger = LogFactory.getGlobalLog();

	private String transName;

	private Class<?>transUrl;

	private String transMethod;

	private Map<String, Class<?>> transproperties = new ConcurrentHashMap<>();

	public DefaultTransDefinition() {
	}


	@Override
	public String getTransName() {
		return transName;
	}

	@Override
	public String getTransUrl() {
		return transName.getClass().getName();
	}

	@Override
	public String getTransMethod() {
		return transMethod;
	}

	@Override
	public void setTransMethod(String method) {
		transMethod=method;
	}

	@Override
	public void setTransUrl(String url) {
		try {
			transUrl=Class.forName(url);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE,"找不到定义的交易类：",e);
		}
	}

	@Override
	public Map<String, Class<?>> getTransproperties() {
		return transproperties;
	}

	@Override
	public Class<?> getTranpropertiesType(String propertiesName) {
		return transproperties.get(propertiesName);
	}

	@Override
	public void setTransproperties(String name, Class<?> type) {
		transproperties.put(name,type);
	}

	@Override
	public void setTransproperties(String name, String type) {
		Class<?>thisType=Object.class;
		try {
			thisType=Class.forName(type);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE,"传入参数类型设置错误：设置类型为："+type+" ",e);
		}
		transproperties.put(name,thisType);
	}

	@Override
	public void setTransName(String name) {
		transName=name;
	}

	@Override
	public boolean containProperties(String propertyName) {
		return transproperties.containsKey(propertyName);
	}

}
