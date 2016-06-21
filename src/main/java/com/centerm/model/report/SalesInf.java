package com.centerm.model.report;

public class SalesInf{
    private String productName;
    
    private String menutpName;
    
    private Integer status;
    
    private String menutpId;

	private Integer sales;
    
    private String mchntCd;

    private String date;

    private String productId;
    
    private String startDate;
    
    private String endDate;
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMenutpName() {
		return menutpName;
	}

	public void setMenutpName(String menutpName) {
		this.menutpName = menutpName;
	}

	public String getMenutpId() {
		return menutpId;
	}

	public void setMenutpId(String menutpId) {
		this.menutpId = menutpId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd == null ? null : mchntCd.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
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

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }
}