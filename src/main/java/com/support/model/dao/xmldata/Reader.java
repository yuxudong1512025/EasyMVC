package com.support.model.dao.xmldata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.publicgroup.util.log.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.support.model.entity.User;

public class Reader {
	private static final Logger logger= LogFactory.getGlobalLog();

	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	private Document document;

	public Reader() {
	}

	public void setUrl(String url) {
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.parse(url);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.log(Level.SEVERE,"xml加载错误 ",e);
		}
	}

	public Reader(String url) {
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.parse(url);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.log(Level.SEVERE,"xml加载错误 ",e);
		}
	}

	public Set readAllData() {
		Set<User> userList = new HashSet<>();

		Element root = document.getDocumentElement();

		NodeList nList = root.getElementsByTagName("user");
		for (int i = 0; i < nList.getLength(); i++) {
			Element user = (Element) nList.item(i);
			String username = user.getAttribute("name");
			String password = user.getAttribute("password");
			String account = user.getAttribute("account");
			userList.add(new User(username, password, new BigDecimal(account)));
		}

		return userList;
	}

}
