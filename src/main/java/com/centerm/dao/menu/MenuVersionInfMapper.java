package com.centerm.dao.menu;

import com.centerm.base.BaseMapper;
import com.centerm.model.menu.MenuVersionInf;

public interface MenuVersionInfMapper extends BaseMapper<MenuVersionInf> {
	public void versionIncrement(String mchntCd);
}