package com.centerm.dao.template;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.menu.MenuTypeInf;
import com.centerm.model.template.MenuTypeTemplateInf;

public interface MenuTypeTemplateInfMapper extends BaseMapper<MenuTypeTemplateInf>{
	public int queryMaxPriority(String mchntCd);
	
	public int isNameExisted(MenuTypeTemplateInf menuType);
	
	public List<MenuTypeTemplateInf> tree(String mchntCd);
}