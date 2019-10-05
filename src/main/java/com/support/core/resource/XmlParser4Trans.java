package com.support.core.resource;

import com.publicgroup.util.Assert;
import com.publicgroup.util.log.LogFactory;
import com.support.core.config.DefaultTransDefinition;
import com.support.core.config.TransDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class XmlParser4Trans {

	private static final Logger logger = LogFactory.getGlobalLog();

	private static Map<String, TransDefinition> transDefinitions = new HashMap();

	private XmlParser4Trans(){};

	public static Map parser(Document document){

		Element root = document.getDocumentElement();
		logger.info("根节点标记" + root.getTagName());

		NodeList nodeList = root.getElementsByTagName("trans");
		for(int i = 0; i < nodeList.getLength(); ++i) {
			Element node = (Element) nodeList.item(i);
			String transName = node.getAttribute("name");
			String transUrl = node.getAttribute("url");
			String transMethod=node.getAttribute("method");

			TransDefinition transDefinition=new DefaultTransDefinition();
			transDefinition.setTransUrl(transUrl);
			transDefinition.setTransName(transName);
			transDefinition.setTransMethod(transMethod);

			NodeList properties = node.getElementsByTagName("property");

			for(int j = 0; j < properties.getLength(); ++j) {
				Element property = (Element)properties.item(j);
				String name = property.getAttribute("name");
				String type = property.getAttribute("type");
				if (Assert.isEffectiveString(name) && Assert.isEffectiveString(type)) {
					transDefinition.setTransproperties(name,type);
				}
			}
			transDefinitions.put(transName,transDefinition);
		}
		return transDefinitions;
	}

}
