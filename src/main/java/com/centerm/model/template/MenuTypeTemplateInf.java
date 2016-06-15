package com.centerm.model.template;

import java.util.List;

public class MenuTypeTemplateInf {
    private String menutpId;

    private String mchntCd;

    private String menutpName;

    private Integer priority;
    
    private Integer status;
    
    private List<MenuTemplateInf> menus;
    /**
     * 该分类单品数
     */
    private Integer menuNum;
    
    public List<MenuTemplateInf> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuTemplateInf> menus) {
		this.menus = menus;
	}

	public Integer getMenuNum() {
		return menuNum;
	}

	public void setMenuNum(Integer menuNum) {
		this.menuNum = menuNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMenutpId() {
        return menutpId;
    }

    public void setMenutpId(String menutpId) {
        this.menutpId = menutpId;
    }

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd == null ? null : mchntCd.trim();
    }

    public String getMenutpName() {
        return menutpName;
    }

    public void setMenutpName(String menutpName) {
        this.menutpName = menutpName == null ? null : menutpName.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}