package com.centerm.dao.menu;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.menu.ProductAttrTypeInf;

public interface ProductAttrTypeInfMapper extends BaseMapper<ProductAttrTypeInf>{
    public void insertbatch(List<ProductAttrTypeInf> productAttrTypes);

	public void deleteByProductId(String productId);
}