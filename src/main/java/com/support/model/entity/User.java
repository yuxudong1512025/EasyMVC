package com.support.model.entity;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return Objects.equals(userName, user.userName) &&
				Objects.equals(password, user.password) &&
				Objects.equals(account, user.account);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userName, password, account);
	}
}

