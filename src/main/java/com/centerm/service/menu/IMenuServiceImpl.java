package com.centerm.service.menu;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.menu.MenuInf;

public interface IMenuServiceImpl {
	public List<MenuInf> list(MenuInf menu, Page page) throws Exception;
	
	public int del(MenuInf menu);
	
	public int add(MenuInf menu);
	
	public int update(MenuInf menu);
}