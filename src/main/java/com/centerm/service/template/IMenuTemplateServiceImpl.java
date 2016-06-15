package com.centerm.service.template;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.menu.MenuTypeInf;
import com.centerm.model.template.MenuTemplateInf;

public interface IMenuTemplateServiceImpl {
	public List<MenuTemplateInf> list(MenuTemplateInf menu, Page page) throws Exception;
	
	public int del(MenuTemplateInf menu);
	
	public int add(MenuTemplateInf menu);
	
	public int update(MenuTemplateInf menu);
	
}