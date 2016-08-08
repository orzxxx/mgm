package com.centerm.service.template;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.menu.ProductAttrTypeInf;
import com.centerm.model.template.MenuTemplateInf;

public interface IMenuTemplateService {
	public List<MenuTemplateInf> list(MenuTemplateInf menu, Page page) throws Exception;
	
	public void del(MenuTemplateInf menu);
	
	public void update(MenuTemplateInf menu, List<ProductAttrTypeInf> productAttrTypes);

	public void add(MenuTemplateInf menu, List<ProductAttrTypeInf> productAttrTypes);
	
}