package com.centerm.model.menu;

import java.math.BigDecimal;
import java.util.List;

public class ComboInf {
    private String productId;

    private String productName;

    private String menutpId;

    private String mchntCd;

    private BigDecimal price;

    private BigDecimal oriPrice;

    private String pictureLink;

    private String productDetail;

    private String taste;

    private Integer status;

    private Integer priority;

    private String specifications;
    
    private InventoryInf inventory;
    
    private List<ComboDetailInf> comboDetails;
    
    public List<ComboDetailInf> getComboDetails() {
		return comboDetails;
	}

	public void setComboDetails(List<ComboDetailInf> comboDetails) {
		this.comboDetails = comboDetails;
	}
    
    public InventoryInf getInventory() {
		return inventory;
	}

	public void setInventory(InventoryInf inventory) {
		this.inventory = inventory;
	}
    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getMenutpId() {
        return menutpId;
    }

    public void setMenutpId(String menutpId) {
        this.menutpId = menutpId == null ? null : menutpId.trim();
    }

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd == null ? null : mchntCd.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(BigDecimal oriPrice) {
        this.oriPrice = oriPrice;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink == null ? null : pictureLink.trim();
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail == null ? null : productDetail.trim();
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste == null ? null : taste.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications == null ? null : specifications.trim();
    }
}