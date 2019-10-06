package com.support.core.controller;

import com.publicgroup.annotation.Autowired;
import com.publicgroup.annotation.Component;
import com.publicgroup.factory.BeanFactory;
import com.publicgroup.util.Assert;
import com.publicgroup.util.log.LogFactory;
import com.support.core.adapter.StringHandlerAdapter;
import com.support.core.config.TransDefinition;
import com.support.core.mapping.HandleMappingimpl;

import com.support.core.resolver.StringViewResolver;
import com.support.exception.DataNeedACommandException;
import com.support.exception.NoSuchCommandException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yuxudong
 */
@Component
public class DefaultDispatcherController implements DispatcherController, Session {
	private static final Logger logger = LogFactory.getGlobalLog();
	private Map<String, Object> session = new HashMap<>(64);
	private BeanFactory beanFactory;

	@Autowired
	private HandleMappingimpl handleMappingimpl;

	private StringHandlerAdapter stringHandlerAdapter;

	public DefaultDispatcherController(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public DefaultDispatcherController() {
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public void setResource(String resource) {
		handleMappingimpl.getResource(resource);
	}

	@Override
	public Map request(String request) {
		return StringParser.sendDataFormat(request);
	}

	@Override
	public String response(Map data) {
		String command;

		if (Assert.isNotNull(data.get("Command"))) {
			StringViewResolver stringViewResolver= (StringViewResolver) data.get("Command");
			return StringViewResolver.show(stringViewResolver,data.get(stringViewResolver.getKey()));
		} else {
			try {
				throw new DataNeedACommandException();
			} catch (DataNeedACommandException e) {
				logger.log(Level.SEVERE, "需要设置返回指令",e);
			}
		}
		return "Response error";
	}


	@Override
	public Map resolveData(String requestUrl, Map data) throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
		TransDefinition transDefinition = null;

		if (handleMappingimpl.containsTrans(requestUrl)) {
			transDefinition = handleMappingimpl.getTransDefinition(requestUrl);
		} else {
			logger.log(Level.SEVERE, "未定义交易");
			return null;
		}
		stringHandlerAdapter.setSession(this);
		stringHandlerAdapter.setBeanFactory(this.beanFactory);

		return (Map) stringHandlerAdapter.doHandler(transDefinition, data);
	}

	@Override
	public Object getSession(String name) {
		return session.get(name);
	}

	@Override
	public void setSession(String name, Object value) {
		session.put(name, value);
	}

	@Override
	public void removeSession(String name) {
		session.remove(name);
	}

	@Override
	public boolean containSession(String name) {
		return session.containsKey(name);
	}

	public String execute(String input) {
		try {
			Map<String, Object> data = request(input);
			data.put("session", getSession());
			Map<String, Object> executeData = resolveData(data.get("method").toString(), data);
			return response(executeData);
		} catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
			logger.log(Level.SEVERE, "执行失败：", e);
		}
		return "error";
	}

	public void setHandleMappingimpl(HandleMappingimpl handleMappingimpl) {
		this.handleMappingimpl = handleMappingimpl;
	}

	public void setStringHandlerAdapter(StringHandlerAdapter stringHandlerAdapter) {
		this.stringHandlerAdapter = stringHandlerAdapter;
	}

	public Map getSession() {
		return this.session;
	}
}
