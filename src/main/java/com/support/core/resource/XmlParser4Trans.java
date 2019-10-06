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

		NodeList nodeList = root.getElementsByTagName("tran");
		for(int i = 0; i < nodeList.getLength(); ++i) {
			Element node = (Element) nodeList.item(i);
			String transName = node.getAttribute("name");
			String transUrl = node.getAttribute("url");
			String transMethod=node.getAttribute("method");

			TransDefinition transDefinition=new DefaultTransDefinition();
			transDefinition.setTransUrl(transUrl);
			transDefinition.setTransName(transName);
			transDefinition.setTransMethod(transMethod);

			Element rules= (Element) node.getElementsByTagName("rules").item(0);
			if(Assert.isNotNull(rules)){
				NodeList rulelist=rules.getElementsByTagName("rule");
				if(Assert.isNotNull(rulelist)){
					for(int r = 0; r < rulelist.getLength(); ++r) {
						Element rule = (Element) rulelist.item(i);
						String ruleName=rule.getAttribute("name");
						transDefinition.setRule(ruleName);
					}
				}
			}
			transDefinitions.put(transName,transDefinition);
		}
		return transDefinitions;
	}

}
