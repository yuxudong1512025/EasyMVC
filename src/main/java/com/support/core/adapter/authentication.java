package com.support.core.adapter;

public interface authentication {

	boolean userExist();

	void addUser(String userName, String password);

	void removeUser();
}
