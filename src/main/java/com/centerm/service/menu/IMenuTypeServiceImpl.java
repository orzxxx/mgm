package com.centerm.service.menu;

import java.util.List;
import java.util.Map;

import com.centerm.base.Page;
import com.centerm.model.menu.MenuTypeInf;

public interface IMenuTypeServiceImpl {
	public List<MenuTypeInf> list(MenuTypeInf menuType, Page page) throws Exception;
	
	public void del(int menutpId);
	
	public void add(MenuTypeInf menuType);
	
	public void addTree(List<MenuTypeInf> menuTypes) throws Exception;
	
	public void update(MenuTypeInf menuType);
	
	public List<MenuTypeInf> tree(String mchntCd, boolean needCombo);
}
