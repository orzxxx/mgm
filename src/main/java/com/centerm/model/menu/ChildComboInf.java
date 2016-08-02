package com.centerm.model.menu;

import java.math.BigDecimal;
import java.util.List;

public class ChildComboInf {
    private Integer id;

    private Integer comboTypeId;

    private String singleName;

    private BigDecimal singlePrice;

    private Integer exchangeProductFlag;
    
    private Integer priority;
    
    public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
    
    private List<ProductAttrTypeInf> productAttrTypes;
    
    public List<ProductAttrTypeInf> getProductAttrTypes() {
		return productAttrTypes;
	}

	public void setProductAttrTypes(List<ProductAttrTypeInf> productAttrTypes) {
		this.productAttrTypes = productAttrTypes;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComboTypeId() {
        return comboTypeId;
    }

    public void setComboTypeId(Integer comboTypeId) {
        this.comboTypeId = comboTypeId;
    }

    public String getSingleName() {
        return singleName;
    }

    public void setSingleName(String singleName) {
        this.singleName = singleName == null ? null : singleName.trim();
    }

    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }

    public Integer getExchangeProductFlag() {
        return exchangeProductFlag;
    }

    public void setExchangeProductFlag(Integer exchangeProductFlag) {
        this.exchangeProductFlag = exchangeProductFlag;
    }
}