package com.support.core.mapping;

import com.support.core.config.TransDefinition;

import java.util.Map;

public interface handleMapping {

	TransDefinition getTransUrl(String transName);

	void setTrans(String transName,String transUrl);

	boolean containsTrans(String transName);
}
