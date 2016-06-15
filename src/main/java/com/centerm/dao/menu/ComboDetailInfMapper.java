package com.centerm.dao.menu;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.menu.ComboDetailInf;

public interface ComboDetailInfMapper extends BaseMapper<ComboDetailInf>{
	
	public int insertbatch(List<ComboDetailInf> comboDetails);
	
	public int deleteByComboId(String comboId);

	public List<ComboDetailInf> findByComboId(String comboId);
}