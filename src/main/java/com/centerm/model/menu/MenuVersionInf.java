package com.centerm.model.menu;

public class MenuVersionInf {
    private String mchntCd;

    private Integer menuVersion;

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd == null ? null : mchntCd.trim();
    }

    public Integer getMenuVersion() {
        return menuVersion;
    }

    public void setMenuVersion(Integer menuVersion) {
        this.menuVersion = menuVersion;
    }
}