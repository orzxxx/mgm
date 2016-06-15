package com.centerm.model.mchnt;

public class FrchseInf {
    private String frchseCd;

    private String frchseName;

    private String userId;

    private String userName;

    private String idCard;

    private String mobile;

    private String mchntAddr;

    private Integer status;

    public String getFrchseCd() {
        return frchseCd;
    }

    public void setFrchseCd(String frchseCd) {
        this.frchseCd = frchseCd == null ? null : frchseCd.trim();
    }

    public String getFrchseName() {
        return frchseName;
    }

    public void setFrchseName(String frchseName) {
        this.frchseName = frchseName == null ? null : frchseName.trim();
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
}