package com.support.core.config;

import java.util.Map;

public interface TransDefinition {

	String getTransName();

	String getTransUrl();

	String getTransMethod();

	void setTransMethod(String method);

	void setTransUrl(String url);

	void setTransName(String name);


}
