package com.support.core.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface DispatcherController {

	Map Request(String request);

	String Response(Map data);

	Map resolveData(String requestUrl,Map data) throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException;

}
