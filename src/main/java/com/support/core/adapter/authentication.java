package com.support.core.adapter;

public interface authentication {

	boolean userExist(String userName);

	void AddUser(String userName,String password);

	void removeUser(String userName);
}
