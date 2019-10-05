package com.support.core.config;



public interface TransDefinition {

	String getTransName();

	String getTransUrl();

	String getTransMethod();

	void removeRule(String rule);

	boolean containRule(String rule);

	void setTransMethod(String method);

	void setTransUrl(String url);

	void setTransName(String name);

	void setRule(String rule);
}
