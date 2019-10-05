package com.support.core.controller;

import com.publicgroup.annotation.Autowired;
import com.publicgroup.annotation.Component;
import com.publicgroup.factory.BeanFactory;
import com.publicgroup.util.log.LogFactory;
import com.support.core.adapter.StringHandlerAdapter;
import com.support.core.config.TransDefinition;
import com.support.core.mapping.HandleMappingimpl;
import com.support.core.mapping.handleMapping;
import com.support.core.resolver.StringViewResolver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yuxudong
 */
@Component
public class DefaultDispatcherController implements DispatcherController,Session{
	private final static Logger logger= LogFactory.getGlobalLog();
	private Map<String,Object>session=new HashMap<>(64);
	private BeanFactory beanFactory;

	@Autowired
	private HandleMappingimpl handleMappingimpl;

	private StringHandlerAdapter stringHandlerAdapter;

	public DefaultDispatcherController(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public DefaultDispatcherController(){}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	public void setResource(String resource){
		handleMappingimpl.getResource(resource);
	}

	@Override
	public Map Request(String request) {
		return StringParser.sendDataFormat(request);
	}

	@Override
	public String Response(Map data) {
		String Command;

			if (data.get("Command") instanceof String) {
				Command = data.get("Command").toString();
				if (StringViewResolver.containsCommand(Command)){
					return StringViewResolver.show(Command,data.get(Command));
				}else{
					try{
						throw new RuntimeException();
					}catch (RuntimeException e) {
						logger.log(Level.SEVERE,"Command未设置");
					}
				}
			}else{
				try{
					throw new RuntimeException();
				}catch (RuntimeException e) {
					logger.log(Level.SEVERE,"需要设置返回指令");
				}
			}
		return "Response error";
	}


	@Override
	public Map resolveData(String requestUrl, Map data) throws IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		Map<String,Object>resolveDataMap=new HashMap<>();
		TransDefinition transDefinition=null;

		if(handleMappingimpl.containsTrans(requestUrl)){
			transDefinition=handleMappingimpl.getTransDefinition(requestUrl);
		}else{
			logger.log(Level.SEVERE,"未定义交易");
			return null;
		}
		stringHandlerAdapter.setSession(this);
		stringHandlerAdapter.setBeanFactory(this.beanFactory);

		return (Map) stringHandlerAdapter.execute(transDefinition,data);
	}

	@Override
	public Object getSession(String name) {
		return session.get(name);
	}

	@Override
	public void setSession(String name, Object value) {
		session.put(name,value);
	}

	@Override
	public void removeSession(String name) {
		session.remove(name);
	}

	@Override
	public boolean containSession(String name) {
		return session.containsKey(name);
	}

	public String execute(String input){
		try{
			Map<String,String>data=Request(input);

			Map<String,Object>executeData=resolveData(data.get("method"),data);

			return Response(executeData);
		} catch (IllegalAccessException|InvocationTargetException|ClassNotFoundException e) {
			logger.log(Level.SEVERE,"",e);
		}
		return "error";
	}

	public void setHandleMappingimpl(HandleMappingimpl handleMappingimpl) {
		this.handleMappingimpl = handleMappingimpl;
	}

	public void setStringHandlerAdapter(StringHandlerAdapter stringHandlerAdapter) {
		this.stringHandlerAdapter = stringHandlerAdapter;
	}
}
