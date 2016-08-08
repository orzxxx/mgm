package com.centerm.service.menu;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.menu.ChildComboTypeInf;
import com.centerm.model.menu.ComboDetailInf;
import com.centerm.model.menu.ComboInf;

public interface IComboService {
	public List<ComboInf> list(ComboInf combo, Page page) throws Exception;
	
	public void del(ComboInf combo);
	
	public void update(ComboInf combo, List<ChildComboTypeInf> childComboTypes);

	public void add(ComboInf combo, List<ChildComboTypeInf> childComboTypes);

	public List<ChildComboTypeInf> geChildren(String comboId);
}