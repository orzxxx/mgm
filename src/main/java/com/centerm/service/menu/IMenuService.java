package com.centerm.service.menu;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.menu.MenuInf;
import com.centerm.model.menu.ProductAttrTypeInf;

public interface IMenuService {
	public List<MenuInf> list(MenuInf menu, Page page) throws Exception;
	
	public void del(MenuInf menu);
	
	//public int add(MenuInf menu);
	
	public void update(MenuInf menu, List<ProductAttrTypeInf> productAttrTypes);

	public void add(MenuInf menu, List<ProductAttrTypeInf> productAttrTypes);

	public void setPackingBoxNum(MenuInf menu);

	public void shelve(MenuInf menu) throws Exception;

	public List<MenuInf> queryMenuAndCombo(MenuInf menu, Page page) throws Exception;
}