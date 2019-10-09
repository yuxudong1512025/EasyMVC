package com.support.core.mapping;

import com.publicgroup.annotation.Autowired;
import com.publicgroup.annotation.Component;
import com.publicgroup.resourcereader.resource.Resource;
import com.publicgroup.resourcereader.resource.ResourceLoader;
import com.publicgroup.util.log.LogFactory;
import com.support.core.config.TransDefinition;
import com.support.core.resource.TransDefinitionRegistry;
import com.support.core.resource.TransResource;
import com.support.core.resource.XmlTransDefinitionReader;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class HandleMappingimpl implements ResourceLoader, TransDefinitionRegistry, handleMapping {
	private static Logger logger = LogFactory.getGlobalLog();
	protected Map<String, TransDefinition> transDefinitionMap = new HashMap<>(256);

	@Autowired
	protected TransResource transResource;

	@Override
	public Resource getResource(String s) {
		transResource.setPath(s);
		refresh();
		return transResource;
	}

	@Override
	public void registerTransDefinition(String transName, TransDefinition transDefinition) {
		transDefinitionMap.put(transName, transDefinition);
	}


	public void setTransResource(TransResource transResource) {
		this.transResource = transResource;
	}


	@Override
	public TransDefinition getTransDefinition(String transName) {
		return transDefinitionMap.get(transName);
	}

	@Override
	public boolean containsTrans(String transName) {
		return transDefinitionMap.containsKey(transName);
	}

	@Override
	public String getTransUrl(String transName) {
		return transDefinitionMap.get(transName).getTransUrl();
	}

	protected static class ResourceReaderFactory extends XmlTransDefinitionReader {
		public ResourceReaderFactory(TransDefinitionRegistry registry) {
			super(registry);
		}
	}

	/**
	 * 根据xml实例化transDefinition并注册进map里
	 */
	private void refresh() {
		int count = 0;
		try {
			count = (new ResourceReaderFactory(this).loadTransDefinitions(transResource));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "", e);
		}
		int finalCount = count;
		logger.info(()->"一共初注册了 "+ finalCount +" 个transDefinition");
	}
}
