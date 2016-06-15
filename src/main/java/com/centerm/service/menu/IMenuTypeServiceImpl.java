package com.centerm.service.menu;

import java.util.List;
import java.util.Map;

import com.centerm.base.Page;
import com.centerm.model.menu.MenuTypeInf;

public interface IMenuTypeServiceImpl {
	public List<MenuTypeInf> list(MenuTypeInf menuType, Page page) throws Exception;
	
	public int del(int menutpId);
	
	public int add(MenuTypeInf menuType);
	
	public int addTree(List<MenuTypeInf> menuTypes) throws Exception;
	
	public int update(MenuTypeInf menuType);
	
	public List<MenuTypeInf> tree(String mchntCd, boolean needCombo);
}
