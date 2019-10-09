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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yuxudong
 */
@Component
public class DefaultDispatcherController implements DispatcherController {
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
		//把map解析成结果
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

		//根据输入的方法名从map里拿到对应的transDefinition实例
		if (handleMappingimpl.containsTrans(requestUrl)) {
			transDefinition = handleMappingimpl.getTransDefinition(requestUrl);
		} else {
			logger.log(Level.SEVERE, "未定义交易");
			return null;
		}
		stringHandlerAdapter.setBeanFactory(this.beanFactory);

		//返回方法执行结果
		return (Map) stringHandlerAdapter.doHandler(transDefinition, data);
	}


	//解析并执行输入的命令
	public String execute(String input) {
		try {
			//把输入命令解析后放进map
			Map<String, Object> data = request(input);
			//把session状态放进map
			data.put("session", getSession());
			//根据method名字。调用controller里相应的方法来执行

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
