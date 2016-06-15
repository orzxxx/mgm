package com.centerm.service.menu;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.menu.ComboDetailInf;
import com.centerm.model.menu.ComboInf;

public interface IComboServiceImpl {
	public List<ComboInf> list(ComboInf combo, Page page) throws Exception;
	
	public int del(ComboInf combo);
	
	public int add(ComboInf combo);
	
	public int update(ComboInf combo);

	public List<ComboDetailInf> getDetails(String comboId);
}