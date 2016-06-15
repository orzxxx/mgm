package com.centerm.dao.mchnt;

import com.centerm.base.BaseMapper;
import com.centerm.model.mchnt.FrchseInf;

public interface FrchseInfMapper extends BaseMapper<FrchseInf>{
	public FrchseInf selectByUserId(String userId);
}