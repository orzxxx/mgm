package com.centerm.service.mchnt;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.mchnt.FrchseInf;

public interface IFrchseService {
	public List<FrchseInf> list(FrchseInf frchse, Page page) throws Exception;
	
	public int del(int id);
	
	public int add(FrchseInf frchse);
	
	public void update(FrchseInf frchse);
	
	public FrchseInf get(String mchntCd);
	
}