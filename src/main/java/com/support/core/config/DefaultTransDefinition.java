package com.support.core.config;

import com.publicgroup.util.log.LogFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultTransDefinition implements TransDefinition {
	private static final Logger logger = LogFactory.getGlobalLog();

	private String transName;

	private Class<?>transUrl;

	private String transMethod;


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
	public void setTransName(String name) {
		transName=name;
	}

}
