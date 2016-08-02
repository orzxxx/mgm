package com.centerm.model.menu;

import java.util.List;

public class ChildComboTypeInf {
    private Integer id;

    private String comboTypeName;

    private String productId;

    private Integer exchangeFlag;
    
    private Integer priority;
    
    public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	private List<ChildComboInf> childCombos;
    
    private List<ProductAttrTypeInf> productAttrTypes;
	
    public List<ProductAttrTypeInf> getProductAttrTypes() {
		return productAttrTypes;
	}

	public void setProductAttrTypes(List<ProductAttrTypeInf> productAttrTypes) {
		this.productAttrTypes = productAttrTypes;
	}
    
    public List<ChildComboInf> getChildCombos() {
		return childCombos;
	}

	public void setChildCombos(List<ChildComboInf> childCombos) {
		this.childCombos = childCombos;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComboTypeName() {
        return comboTypeName;
    }

    public void setComboTypeName(String comboTypeName) {
        this.comboTypeName = comboTypeName == null ? null : comboTypeName.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public Integer getExchangeFlag() {
        return exchangeFlag;
    }

    public void setExchangeFlag(Integer exchangeFlag) {
        this.exchangeFlag = exchangeFlag;
    }
}