package com.centerm.service.report;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.report.SalesInf;

public interface ISalesServiceImpl {
	public List<SalesInf> list(SalesInf sales, Page page) throws Exception;
	
	public int del(int id);
	
	public int add(SalesInf sales);
	
	public int update(SalesInf sales);
}