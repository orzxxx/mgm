package com.centerm.service.menu;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.menu.MenuTypeInf;

public interface IMenuTypeService {
	public List<MenuTypeInf> list(MenuTypeInf menuType, Page page) throws Exception;
	
	public void del(MenuTypeInf menuType);
	
	public void add(MenuTypeInf menuType);
	
	public void addTree(List<MenuTypeInf> menuTypes) throws Exception;
	
	public void update(MenuTypeInf menuType);
	
	public List<MenuTypeInf> tree(String mchntCd, boolean needCombo);
}
