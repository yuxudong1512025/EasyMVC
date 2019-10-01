package com.support.core.config;

import java.util.Map;

public interface TransDefinition {

	String getTransName();

	String getTransUrl();

	void setTransUrl(String url);

	Map<String,Class<?>> getTransproperties();

	Class<?>getTranpropertiesType(String propertiesName);

	void setTransproperties(String name,Class<?>type);

	void setTransproperties(String name,String type);

	void setTransName(String name);

	boolean containProperties(String propertyName);

}
