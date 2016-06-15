package com.centerm.model.mchnt;

public class MchntInf {
    private String mchntCd;

    private String mchntName;

    private String userId;

    private String userName;

    private String idCard;

    private String mobile;

    private String email;

    private String mchntAddr;

    private Integer status;

    private String inAcct;

    private String alipayNo;

    private String wechatNo;

    private String district;

    private String detailedAddress;
    
    private String mchntLogo;
    
    public String getMchntLogo() {
		return mchntLogo;
	}

	public void setMchntLogo(String mchntLogo) {
		this.mchntLogo = mchntLogo;
	}

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd == null ? null : mchntCd.trim();
    }

    public String getMchntName() {
        return mchntName;
    }

    public void setMchntName(String mchntName) {
        this.mchntName = mchntName == null ? null : mchntName.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMchntAddr() {
        return mchntAddr;
    }

    public void setMchntAddr(String mchntAddr) {
        this.mchntAddr = mchntAddr == null ? null : mchntAddr.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInAcct() {
        return inAcct;
    }

    public void setInAcct(String inAcct) {
        this.inAcct = inAcct == null ? null : inAcct.trim();
    }

    public String getAlipayNo() {
        return alipayNo;
    }

    public void setAlipayNo(String alipayNo) {
        this.alipayNo = alipayNo == null ? null : alipayNo.trim();
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo == null ? null : wechatNo.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress == null ? null : detailedAddress.trim();
    }
}