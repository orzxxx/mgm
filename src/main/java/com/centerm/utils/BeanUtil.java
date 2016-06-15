package com.centerm.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class BeanUtil {

	/**
	 * objbean转map
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> bean2Map(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();

			// 过滤class属性
			if (!key.equals("class")) {
				// 得到property对应的getter方法
				Method getter = property.getReadMethod();
				if(getter!=null) {
					Object value = getter.invoke(obj);
					map.put(key, value);
				} else {
					map.put(key, null);
				}
			}
		}
		return map;
	}
	
	public static Map<String, Object> interfaceBean2Map(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Method[] methods = obj.getClass().getDeclaredMethods();  
        for (Method method : methods) {  
            if (method.getName().startsWith("get")) {  
                String field = method.getName();  
                field = field.substring(field.indexOf("get") + 3);  
                field = field.toLowerCase().charAt(0) + field.substring(1);  
                Object value = method.invoke(obj, (Object[]) null);  
                map.put(field,(null == value ? "" : value));  
            }  
        }
		return map;
	}
	
	// Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean  
    public static Map<String, Object> transMap2Bean(Object obj) throws Exception {
    	Map<String, Object> map = new HashMap<String,Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
        for (PropertyDescriptor property : propertyDescriptors) {  
            String key = property.getName();  
            if (map.containsKey(key)) {
                Object value = map.get(key);  
                // 得到property对应的setter方法  
	            Method setter = property.getWriteMethod();  
	            setter.invoke(obj, value);
            }  
        }  
        return map;
    }  
    
}
