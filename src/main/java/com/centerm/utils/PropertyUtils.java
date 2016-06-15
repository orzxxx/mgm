package com.centerm.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

public class PropertyUtils {
	private static Properties configs = new Properties();
	static {
		try {
			configs.load(PropertyUtils.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperties(String key){
		return configs.getProperty(key);
	}
	
	public static Map<String, String> getAllProperties(){
		Set<Entry<Object, Object>>  sets = configs.entrySet();
		Map map = new HashMap<String, String>();
		for (Entry entry : sets) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}
	
	public static String setProperties(String key, String value){
		configs.put(key, value);
		return value;
	}
}	
