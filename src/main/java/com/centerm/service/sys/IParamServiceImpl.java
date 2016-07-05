package com.centerm.service.sys;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.sys.ParamInf;

public interface IParamServiceImpl {
	public List<ParamInf> list(ParamInf param, Page page) throws Exception;
	
	public int del(int id);
	
	public int add(ParamInf param);
	
	public void update(ParamInf param);
	
	public ParamInf get(ParamInf param) throws Exception;
}