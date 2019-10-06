package com.support.model.dao.xmldata;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.publicgroup.util.Assert;
import com.support.model.entity.User;

public class write {
	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	private Document document;
	
	private String url;
	
	write(String url){
		this.url=url;
		 try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			document=db.parse(url);
			
		} catch (ParserConfigurationException|SAXException|IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void insert(User user) {
		boolean createdoc=false,createroot=false;
		if(!Assert.isNotNull(document)) {
			try {
				document=dbf.newDocumentBuilder().newDocument();
				createdoc=true;
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Element users=document.getElementById("users");
		if(!Assert.isNotNull(user)) {
			users=document.createElement("users");
			createroot=true;
		}
		Element userRecord=document.createElement("user");
		userRecord.setAttribute("name", user.getUserName());
		
		
		users.appendChild(userRecord);
		if(createroot) {
			document.appendChild(users);
		}
		TransformerFactory transformerFactory=TransformerFactory.newInstance();
        try {
        	Transformer transformer=transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yse");
            transformer.transform(new DOMSource(document), new StreamResult(new File(url)));
		} catch (TransformerException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
