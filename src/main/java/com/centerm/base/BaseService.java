/*package com.centerm.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseService<T extends BaseMapper> {
	public T mapper;
	
	public BaseService() throws Exception{
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		if (genericSuperclass instanceof Class<?>) {
		}
		Type rawType = ((ParameterizedType) genericSuperclass)
			.getActualTypeArguments()[0];
		
		this.mapper =  (T) ((Class)rawType).newInstance();
	}
	
	public int deleteByPrimaryKey(String id){
		return this.mapper.deleteByPrimaryKey(id);
	};

    public <T> int insert(T record){
    	return this.mapper.insert(record);
    };

    public <T> int insertSelective(T record){
    	return this.mapper.insertSelective(record);
    };

    public <T> T selectByPrimaryKey(String id){
    	return this.mapper.selectByPrimaryKey(id);
    };

    public <T> int updateByPrimaryKeySelective(T record){
    	return this.mapper.updateByPrimaryKeySelective(record);
    };

    public <T> int updateByPrimaryKey(T record){
    	return this.updateByPrimaryKey(record);
    };
}*/