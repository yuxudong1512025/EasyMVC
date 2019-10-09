package com.support.core.resource;

import com.support.core.config.TransDefinition;

public interface TransDefinitionRegistry {

	void registerTransDefinition(String transName, TransDefinition transDefinition);

}
