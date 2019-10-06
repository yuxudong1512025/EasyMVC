package com.support.model.dao;

import com.support.model.dao.xmldata.Reader;
import com.support.model.dao.xmldata.Writer;
import com.support.model.entity.*;

import java.util.Set;

public class UserDao {
	private Reader reader;
	private Writer writer;

	public void setReader(Reader reader) {
		this.reader = reader;

	}

	public void setWriter(Writer writer) {
		this.writer = writer;

	}

	public User findByUsername(String username) {
		this.reader.setUrl(System.getProperty("user.dir")+"\\src\\main\\resources\\user.xml");
		Set<User> users = reader.readAllData();
		for (User user : users) {
			if (user.getUserName().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public void add(User user) {
		this.writer.setUrl(System.getProperty("user.dir")+"\\src\\main\\resources\\user.xml");
		writer.insert(user);
	}

	public void update(User user) {
		this.writer.setUrl(System.getProperty("user.dir")+"\\src\\main\\resources\\user.xml");
		writer.update(user);
	}

}





	

