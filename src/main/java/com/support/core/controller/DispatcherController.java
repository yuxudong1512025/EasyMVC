package com.support.core.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface DispatcherController {

	Map request(String request);

	String response(Map data);

	Map resolveData(String requestUrl,Map data) throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException;

}
