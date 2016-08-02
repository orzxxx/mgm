package com.centerm.dao.menu;

import com.centerm.base.BaseMapper;
import com.centerm.model.menu.ChildComboInf;

public interface ChildComboInfMapper extends BaseMapper<ChildComboInf>{
	public void deleteByProductId(String productId);
}