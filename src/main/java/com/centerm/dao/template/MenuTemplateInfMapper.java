package com.centerm.dao.template;

import com.centerm.base.BaseMapper;
import com.centerm.model.template.MenuTemplateInf;

public interface MenuTemplateInfMapper extends BaseMapper<MenuTemplateInf>{
	public int queryMaxPriority(String mchntCd);
	
	public int isNameExisted(MenuTemplateInf menu);
}