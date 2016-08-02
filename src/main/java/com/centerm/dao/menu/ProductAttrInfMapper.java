package com.centerm.dao.menu;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.menu.ProductAttrInf;

public interface ProductAttrInfMapper extends BaseMapper<ProductAttrInf>{

	public void insertbatch(List<ProductAttrInf> attrs);

	public void deleteByProductId(String productId);
	
	public void deleteByComboId(String ComboId);
	
}