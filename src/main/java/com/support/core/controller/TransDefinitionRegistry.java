package com.support.core.controller;

import com.support.core.config.TransDefinition;

public interface TransDefinitionRegistry {

	void registerTransDefinition(String transName, TransDefinition transDefinition);

	TransDefinition removeTransDefinition(String transName);
}
