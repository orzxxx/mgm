package com.centerm.service.mchnt;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.mchnt.OperatorInf;

public interface IOperatorServiceImpl {
	public List<OperatorInf> list(OperatorInf operator, Page page) throws Exception;
	
	public int del(OperatorInf operator);
	
	public int add(OperatorInf operator);
	
	public int update(OperatorInf operator);

	public void resetPwd(OperatorInf operator, String newPwd);
}