package com.centerm.service.menu;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.menu.ComboDetailInf;
import com.centerm.model.menu.ComboInf;

public interface IComboServiceImpl {
	public List<ComboInf> list(ComboInf combo, Page page) throws Exception;
	
	public void del(ComboInf combo);
	
	public void add(ComboInf combo);
	
	public void update(ComboInf combo);

	public List<ComboDetailInf> getDetails(String comboId);
}