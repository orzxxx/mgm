package com.centerm.service.mchnt;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.mchnt.FrchseInf;
import com.centerm.model.mchnt.MchntInf;

public interface IFrchseServiceImpl {
	public List<FrchseInf> list(FrchseInf frchse, Page page) throws Exception;
	
	public int del(int id);
	
	public int add(FrchseInf frchse);
	
	public int update(FrchseInf frchse);
	
	public FrchseInf get(String mchntCd);
	
}