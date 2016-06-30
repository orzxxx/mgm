package com.centerm.dao.template;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.template.ProductAttrTypeTemplateInf;

public interface ProductAttrTypeTemplateInfMapper extends BaseMapper<ProductAttrTypeTemplateInf>{

	void deleteByProductId(String productId);

	List<ProductAttrTypeTemplateInf> queryByProductId(String productId);
	
}
