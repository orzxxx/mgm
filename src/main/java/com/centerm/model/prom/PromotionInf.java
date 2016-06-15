package com.centerm.model.prom;

public class PromotionInf {
    private String productId;

    private String mchntCd;

    private String pictureLink;
    
    private String promotionInf;
    
	private PromotionProduct product;
	    
    public String getPromotionInf() {
		return promotionInf;
	}

	public void setPromotionInf(String promotionInf) {
		this.promotionInf = promotionInf;
	}

	public PromotionProduct getProduct() {
		return product;
	}

	public void setProduct(PromotionProduct product) {
		this.product = product;
	}

	public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd == null ? null : mchntCd.trim();
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink == null ? null : pictureLink.trim();
    }
}