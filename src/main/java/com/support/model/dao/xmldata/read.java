package com.support.model.dao.xmldata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.support.model.entity.User;

public class read {
	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	private Document document;
	
	read(String url){
		 try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			document=db.parse(url);
			
		} catch (ParserConfigurationException|SAXException|IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Set readAllData() {
		Set<User>userlist=new HashSet<>();
		
		Element root = document.getDocumentElement();

		NodeList nList=root.getElementsByTagName("user");
		for(int i=0;i<nList.getLength();i++) {
			Element user = (Element)nList.item(i);
			String username=user.getAttribute("name");
			String password=user.getAttribute("password");
			String account=user.getAttribute("account");
			userlist.add(new User(username,password,Double.valueOf(account)));
		}
		
		return userlist;
	}


	public static void main(String ...arg) {
		read xmlread=new read(read.class.getResource("/user.xml").getFile());
		Set<User> data=xmlread.readAllData();
		for(User user:data) {
			System.out.println(user.getUserName()+" "+user.getPassword());
		}
	}
}
