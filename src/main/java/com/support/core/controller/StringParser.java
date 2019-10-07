package com.support.core.controller;

import com.publicgroup.util.log.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class StringParser {

	public static Map sendDataFormat(String input) {
		Map<String, String> data = new HashMap<>();
		String[] stringdata = input.split("\\?");
		String[] split=stringdata[0].split("\\.");
		data.put("method", split[0]);
//		data.put("method", stringdata[0]);
		if (stringdata.length >= 2) {
			input = stringdata[1];
			stringdata = input.split("\\&");
			for (String dt : stringdata) {
				String[] temp = dt.split("\\=");
				if (temp.length >= 2) {
					data.put(temp[0], temp[1]);
				} else {
					data.put(temp[0], "");
				}

			}
		}
		return data;
	}

}
