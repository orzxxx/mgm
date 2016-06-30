package com.centerm.model.menu;

import java.util.List;

public class ProductAttrTypeInf {
    private Integer attrTypeId;

    private String attrTypeName;

    private String mchntCd;
    
    private String productId;
    
    private List<ProductAttrInf> productAttrs;
    
    public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

    public List<ProductAttrInf> getProductAttrs() {
		return productAttrs;
	}

	public void setProductAttrs(List<ProductAttrInf> productAttrs) {
		this.productAttrs = productAttrs;
	}

	public Integer getAttrTypeId() {
        return attrTypeId;
    }

    public void setAttrTypeId(Integer attrTypeId) {
        this.attrTypeId = attrTypeId;
    }

    public String getAttrTypeName() {
        return attrTypeName;
    }

    public void setAttrTypeName(String attrTypeName) {
        this.attrTypeName = attrTypeName == null ? null : attrTypeName.trim();
    }

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd == null ? null : mchntCd.trim();
    }
}