package com.centerm.dao.template;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.menu.ProductAttrInf;
import com.centerm.model.template.ProductAttrTemplateInf;

public interface ProductAttrTemplateInfMapper extends BaseMapper<ProductAttrTemplateInf>{

	void deleteByProductId(String productId);

	void insertbatch(List<ProductAttrInf> attrs);
	
}