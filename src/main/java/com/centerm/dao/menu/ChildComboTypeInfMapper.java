package com.centerm.dao.menu;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.menu.ChildComboTypeInf;

public interface ChildComboTypeInfMapper extends BaseMapper<ChildComboTypeInf>{
	public List<ChildComboTypeInf> getChildrenByComboId(String comboId);

	public void deleteByProductId(String productId);
}