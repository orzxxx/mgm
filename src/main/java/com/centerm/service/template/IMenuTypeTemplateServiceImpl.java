package com.centerm.service.template;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.menu.MenuTypeInf;
import com.centerm.model.template.MenuTypeTemplateInf;

public interface IMenuTypeTemplateServiceImpl {
	public List<MenuTypeTemplateInf> list(MenuTypeTemplateInf menuType, Page page) throws Exception;
	
	public int del(int menutpId);
	
	public int add(MenuTypeTemplateInf menuType);
	
	public int update(MenuTypeTemplateInf menuType);
	
	public List<MenuTypeTemplateInf> tree(String mchntCd);
}
