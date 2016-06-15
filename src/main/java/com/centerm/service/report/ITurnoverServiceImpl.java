package com.centerm.service.report;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.report.TurnoverInf;

public interface ITurnoverServiceImpl {
	public List<TurnoverInf> list(TurnoverInf turnover, Page page) throws Exception;
	
	public int del(int id);
	
	public int add(TurnoverInf turnover);
	
	public int update(TurnoverInf turnover);
}