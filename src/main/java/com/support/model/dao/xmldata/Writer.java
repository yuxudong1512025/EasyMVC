package com.support.model.dao.xmldata;

import java.io.File;
import java.io.IOException;
import java.util.Set;

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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.publicgroup.util.Assert;
import com.support.model.entity.User;

public class Writer {
	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	private Document document;
	
	private String url;

	public void setUrl(String url){
		this.url=url;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			document=db.parse(url);

		} catch (ParserConfigurationException|SAXException|IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Writer(){}
	Writer(String url){
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
		if(!Assert.isNotNull(document)){
			System.out.println("null");
		}
		Element users=document.getDocumentElement();
		if(!Assert.isNotNull(users)) {
			users=document.createElement("users");
			createroot=true;
		}
		Element userRecord=document.createElement("user");
		userRecord.setAttribute("name", user.getUserName());
		userRecord.setAttribute("password", user.getPassword());
		userRecord.setAttribute("account", String.valueOf(user.getAccount()));


		if(createroot) {
			document.appendChild(users);
		}
		users.appendChild(userRecord);
		writeinfile();
	}

	void update(User user){
		Element users=document.getDocumentElement();

		NodeList nodeList=users.getElementsByTagName("user");
		for(int i=0;i<nodeList.getLength();i++){
			Element userElement=(Element)nodeList.item(i);
			String username=userElement.getAttribute("name");
			if(username.equals(user.getUserName())){
				userElement.setAttribute("password",user.getPassword());
				userElement.setAttribute("account",String.valueOf(user.getAccount()));
				users.appendChild(userElement);
				break;
			}
		}
		writeinfile();
	}

	void delete(User user){
		Element users=document.getDocumentElement();

		NodeList nodeList=users.getElementsByTagName("user");
		for(int i=0;i<nodeList.getLength();i++){
			Element userElement=(Element)nodeList.item(i);
			String username=userElement.getAttribute("name");
			if(username.equals(user.getUserName())){
				users.removeChild(userElement);
				break;
			}
		}
		writeinfile();
	}

	private void writeinfile(){
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

	public static void main(String[] args) {
		String url= Writer.class.getResource("/user.xml").getFile();
		Writer datawrite=new Writer(url);
		datawrite.update(new User("王郁琦","122322",123.0));
		Reader reader=new Reader(url);
		Set<User>users= reader.readAllData();
		for (User user:users){
			System.out.println(user.getUserName()+" "+user.getPassword());
		}
	}
}
