package com.centerm.dao.menu;

import java.util.List;
import java.util.Map;

import com.centerm.base.BaseMapper;
import com.centerm.model.menu.ComboInf;
import com.centerm.model.menu.MenuInf;

public interface MenuInfMapper extends BaseMapper<MenuInf>{
	public int queryMaxPriority(String mchntCd);
	
	public int isNameExisted(MenuInf menu);
	
	public int insertbatch(List<MenuInf> menus);
	
	public List<MenuInf> isNamesExisted(Map param);
	
	public List<ComboInf> isUsedByCombo(String productId);
}