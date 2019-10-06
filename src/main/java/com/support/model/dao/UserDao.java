package com.support.model.dao;

import com.support.model.dao.xmldata.Reader;
import com.support.model.dao.xmldata.Writer;
import com.support.model.entity.*;

public class UserDao {
	private Reader reader;
	private Writer writer;

	public void setReader(Reader reader) {
		this.reader = reader;
		this.reader.setUrl(UserDao.class.getResource("/user.xml").getFile());
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
		this.writer.setUrl(UserDao.class.getResource("/user.xml").getFile());
	}
	//	public User findByUsername(String username) {
//				
//			Document document = reader.Reader(path);
//			Element element = (Element) document					
//					.selectSingleNode("//user[@name='" + username + "']");			
//			if (element == null) {			
//				return null;
//			}
//				User user = new User();			
//				String attrUsername = element.attributeValue("username"); 		
//				String attrPassword = element.attributeValue("password"); 
//				user.setUserName(attrUsername);			
//				user.setPassword(attrPassword);			
//				return user;	
//	}
//
//	public void add(User user) {		
//		SAXReader reader = new SAXReader();		
//		try {			
//			Document document = reader.Reader(path);			// 得到根元素
//			Element root = document.getRootElement();			// 通过跟元素创建新元素			
//			Element userElement = root.addElement("user");			
//			userElement.addAttribute("username", user.getUsername());			
//			userElement.addAttribute("password", user.getPassword()); 			// 保存文档			// 创建输出格式化器			
//			OutputFormat format = new OutputFormat("\t", true); // 缩进使用\t	
//			format.setTrimText(true); // 清空原有的换行和缩进			
//			try {				// 创建XMLWriter				
//				XMLWriter writer = new XMLWriter(new OutputStreamWriter(						
//						new FileOutputStream(path), "utf-8"), format);				
//				writer.Writer(document); // 保存document对象
//				writer.close(); 			
//			} catch (Exception e) {				
//				throw new RuntimeException();	
//			}
//		} catch (DocumentException e) {			// TODO Auto-generated catch block			
//				e.printStackTrace();		
//		}
//	}

}





	

