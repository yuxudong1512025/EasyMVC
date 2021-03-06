package com.support.core.mapping;

import com.support.core.config.TransDefinition;

import java.util.Map;

public interface handleMapping {

	TransDefinition getTransDefinition(String transName);

	boolean containsTrans(String transName);

	String getTransUrl(String transName);
}
