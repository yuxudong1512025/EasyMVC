package com.support.model.entity;

public class User {
	private String userName;
	private String password;
	private Double account;

	public Double getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public void setAccount(Double account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public User(String username, String password, Double account) {
		super();
		this.userName = username;
		this.password = password;
		this.account = account;
	}

	public User() {
		super();
	}
}

