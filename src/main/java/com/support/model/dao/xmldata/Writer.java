package com.support.model.dao.xmldata;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.publicgroup.util.log.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.publicgroup.util.Assert;
import com.support.model.entity.User;

/**
 * @author wangyuqishmily
 */
public class Writer {
	private static final Logger logger= LogFactory.getGlobalLog();

	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	private Document document;

	private String url;

	public void setUrl(String url) {
		this.url = url;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.parse(url);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.log(Level.SEVERE,"xml加载错误 ",e);
		}
	}

	public Writer() {
	}

	public Writer(String url) {
		this.url = url;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.parse(url);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.log(Level.SEVERE,"xml加载错误 ",e);
		}
	}

	public void insert(User user) {
		boolean createroot = false;
		if (!Assert.isNotNull(document)) {
			try {
				document = dbf.newDocumentBuilder().newDocument();
			} catch (ParserConfigurationException e) {
				logger.log(Level.SEVERE,"document 新建错误 ",e);
			}
		}
		if (!Assert.isNotNull(document)) {
			logger.warning("null");
		}
		Element users = document.getDocumentElement();
		if (!Assert.isNotNull(users)) {
			users = document.createElement("users");
			createroot = true;
		}
		Element userRecord = document.createElement("user");
		userRecord.setAttribute("name", user.getUserName());
		userRecord.setAttribute("password", user.getPassword());
		userRecord.setAttribute("account", String.valueOf(user.getAccount()));


		if (createroot) {
			document.appendChild(users);
		}
		users.appendChild(userRecord);
		writeinfile();
	}

	public void update(User user) {
		Element users = document.getDocumentElement();

		NodeList nodeList = users.getElementsByTagName("user");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element userElement = (Element) nodeList.item(i);
			String username = userElement.getAttribute("name");
			if (username.equals(user.getUserName())) {
				userElement.setAttribute("password", user.getPassword());
				userElement.setAttribute("account", String.valueOf(user.getAccount()));
				users.appendChild(userElement);
				break;
			}
		}
		writeinfile();
	}

	public void delete(User user) {
		Element users = document.getDocumentElement();

		NodeList nodeList = users.getElementsByTagName("user");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element userElement = (Element) nodeList.item(i);
			String username = userElement.getAttribute("name");
			if (username.equals(user.getUserName())) {
				users.removeChild(userElement);
				break;
			}
		}
		writeinfile();
	}

	private void writeinfile() {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yse");
			transformer.transform(new DOMSource(document), new StreamResult(new File(url)));
		} catch (TransformerException e) {
			logger.log(Level.SEVERE,"xml写回错误  ",e);
		}
	}

	public static void main(String[] args) {
		String url = Writer.class.getResource("/user.xml").getFile();
		Writer datawrite = new Writer(url);
		datawrite.update(new User("王郁琦", "122322", 123.0));
		Reader reader = new Reader(url);
		Set<User> users = reader.readAllData();
		for (User user : users) {
			logger.info(user.getUserName() + " " + user.getPassword());
		}
	}
}
