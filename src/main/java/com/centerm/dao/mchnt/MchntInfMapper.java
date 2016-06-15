package com.centerm.dao.mchnt;

import com.centerm.base.BaseMapper;
import com.centerm.model.mchnt.MchntInf;

public interface MchntInfMapper extends BaseMapper<MchntInf>{
	public MchntInf selectByUserId(String userId);
	
	public int isUserIdExisted(MchntInf mchnt);
}