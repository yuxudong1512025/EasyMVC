package com.support.core.controller;


import com.publicgroup.resourcereader.resource.Resource;
import com.publicgroup.resourcereader.resource.ResourceLoader;
import com.publicgroup.resourcereader.resource.XmlDocumentResource;
import com.support.core.config.TransDefinition;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxudong
 */
public class XmlTransDefinitionReader{
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

	public int loadBeanDefinitions(Resource resource) throws Exception {
		return this.doLoadTransDefinitions(resource);
	}

	protected Document doLoadDocument(Resource resource) throws Exception {
		return (new XmlDocumentResource(resource.getFile())).getDocument();
	}

	public int doLoadTransDefinitions(Resource resource)throws Exception{
		Document doc = this.doLoadDocument(resource);
		this.transDefinitions = XmlParser4Trans.parser(doc);
		for (Map.Entry<String,TransDefinition>entry:transDefinitions.entrySet()){
			registry.registerTransDefinition(entry.getKey(),entry.getValue());
		}
		return transDefinitions.size();
	}
}
