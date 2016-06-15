package com.centerm.dao.menu;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.menu.InventoryInf;

public interface InventoryInfMapper extends BaseMapper<InventoryInf>{

	void insertbatch(List<InventoryInf> inventorys);
	
}