package com.support.core.adapter;

public interface authentication {

	boolean userExist();

	void AddUser(String userName,String password);

	void removeUser();
}
