package com.centerm.service.mchnt;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.mchnt.OperatorInf;

public interface IOperatorService {
	public List<OperatorInf> list(OperatorInf operator, Page page) throws Exception;
	
	public void del(OperatorInf operator);
	
	public void add(OperatorInf operator);
	
	public int update(OperatorInf operator);

	public void resetPwd(OperatorInf operator, String newPwd);
}