package com.centerm.dao.menu;

import com.centerm.base.BaseMapper;
import com.centerm.model.menu.ComboInf;

public interface ComboInfMapper extends BaseMapper<ComboInf>{
	public int queryMaxPriority(String mchntCd);
	
	public int isNameExisted(ComboInf combo);
	
	public int updatePackingBoxNumByMchntCd(ComboInf combo);
}