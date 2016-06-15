package com.centerm.dao.mchnt;

import com.centerm.base.BaseMapper;
import com.centerm.model.mchnt.OperatorInf;

public interface OperatorInfMapper extends BaseMapper<OperatorInf>{
	public int deleteByPrimaryKey(OperatorInf operator);
	
	public OperatorInf selectByPrimaryKey(OperatorInf operator);

	public int isIdExisted(OperatorInf operator);
	
}