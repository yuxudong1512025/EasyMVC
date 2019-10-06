package com.support.core.resource;


import com.publicgroup.resourcereader.resource.Resource;
import com.publicgroup.resourcereader.resource.ResourceLoader;
import com.publicgroup.resourcereader.resource.XmlDocumentResource;
import com.publicgroup.util.log.LogFactory;
import com.support.core.config.TransDefinition;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yuxudong
 */
public class XmlTransDefinitionReader {
	private static final Logger logger = LogFactory.getGlobalLog();

	protected final TransDefinitionRegistry registry;
	private ResourceLoader resourceLoader;

	protected Map<String, TransDefinition> transDefinitions = new HashMap();

	public XmlTransDefinitionReader(TransDefinitionRegistry registry) {
		this.registry = registry;
		this.resourceLoader = (ResourceLoader) registry;
	}

	public TransDefinitionRegistry getBeanDefinitionRegistry() {
		return registry;
	}

	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public int loadTransDefinitions(Resource resource) {
		return this.doLoadTransDefinitions(resource);
	}

	protected Document doLoadDocument(Resource resource) {
		try {
			return (new XmlDocumentResource(resource.getFile())).getDocument();
		} catch (ParserConfigurationException | IOException | SAXException e) {
			logger.log(Level.SEVERE, "Xml加载错误 ", e);
		}
		return null;
	}

	public int doLoadTransDefinitions(Resource resource) {
		Document doc = this.doLoadDocument(resource);
		this.transDefinitions = XmlParser4Trans.parser(doc);
		for (Map.Entry<String, TransDefinition> entry : transDefinitions.entrySet()) {
			registry.registerTransDefinition(entry.getKey(), entry.getValue());
		}
		return transDefinitions.size();
	}
}
