package com.support.core.controller;

public interface Session {
	Object getSession(String name);

	void setSession(String name,Object value);

	void removeSession(String name);

	boolean containSession(String name);
}
