package com.centerm.model.menu;

import java.util.List;

public class ComboDetailInf{

	private String comboId;

	private String productId;
	
    private String taste;

    private String specifications;

    private Integer num;
    
    private Double price;
    
    private String productName;
    
    private String allSpec;
    
    private String allTaste;
    
    private String attrCmp;
    /**
     * 选中的属性名,逗号分割
     */
    private String selectedAttr;
    
	private MenuInf product;
	
	public String getSelectedAttr() {
		return selectedAttr;
	}

	public void setSelectedAttr(String selectedAttr) {
		this.selectedAttr = selectedAttr;
	}
    
	public MenuInf getProduct() {
		return product;
	}

	public void setProduct(MenuInf product) {
		this.product = product;
	}

	public String getAttrCmp() {
		return attrCmp;
	}

	public void setAttrCmp(String attrCmp) {
		this.attrCmp = attrCmp;
	}

	public String getAllTaste() {
		return allTaste;
	}

	public void setAllTaste(String allTaste) {
		this.allTaste = allTaste;
	}

	public String getAllSpec() {
		return allSpec;
	}

	public void setAllSpec(String allSpec) {
		this.allSpec = allSpec;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
    
    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId == null ? null : comboId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }
    
    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste == null ? null : taste.trim();
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications == null ? null : specifications.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}