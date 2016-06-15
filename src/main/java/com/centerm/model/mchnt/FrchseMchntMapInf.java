package com.centerm.model.mchnt;

public class FrchseMchntMapInf {
    private String uuid;

    private String frchseCd;

    private String mchntCd;

    private Integer status;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getFrchseCd() {
        return frchseCd;
    }

    public void setFrchseCd(String frchseCd) {
        this.frchseCd = frchseCd == null ? null : frchseCd.trim();
    }

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd == null ? null : mchntCd.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}