package com.centerm.dao.menu;

import java.util.List;
import java.util.Map;

import com.centerm.base.BaseMapper;
import com.centerm.model.menu.MenuTypeInf;

public interface MenuTypeInfMapper extends BaseMapper<MenuTypeInf>{
	public int queryMaxPriority(String mchntCd);
	
	public int isNameExisted(MenuTypeInf menuType);
	
	public List<MenuTypeInf> tree(Map param);
	
	public List<MenuTypeInf> isNamesExisted(Map param);
	
	public int insertbatch(List<MenuTypeInf> menuTypes);
	
	public String getComboMenutpId(String mchntCd);
}