package com.centerm.base;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface BaseMapper<T> {
	
	public int deleteByPrimaryKey(String id);
	
	public int deleteByPrimaryKey(int id);
	
	public int deleteByPrimaryKey(T record);

    public <T> int insert(T record);

    public <T> int insertSelective(T record);

    public <T> T selectByPrimaryKey(String id);

    public <T> int updateByPrimaryKeySelective(T record);

    public <T> int updateByPrimaryKey(T record);
    
    public <T> List<T> query(Map map);
    
}
