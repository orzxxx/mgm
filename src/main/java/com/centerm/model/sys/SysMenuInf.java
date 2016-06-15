package com.centerm.model.sys;

import java.util.ArrayList;
import java.util.List;

public class SysMenuInf {
    private String menuId;

    private String menuNm;

    private String menuUrl;

    private Integer menuSeq;

    private String parentMenuId;

    private String menuEntry;

    private String recCrtDt;

    private String recUpdDt;
    
    private List<SysMenuInf> subMenus = new ArrayList<SysMenuInf>();
    
    public List<SysMenuInf> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<SysMenuInf> subMenus) {
		this.subMenus = subMenus;
	}

	public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm == null ? null : menuNm.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public Integer getMenuSeq() {
        return menuSeq;
    }

    public void setMenuSeq(Integer menuSeq) {
        this.menuSeq = menuSeq;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId == null ? null : parentMenuId.trim();
    }

    public String getMenuEntry() {
        return menuEntry;
    }

    public void setMenuEntry(String menuEntry) {
        this.menuEntry = menuEntry == null ? null : menuEntry.trim();
    }

    public String getRecCrtDt() {
        return recCrtDt;
    }

    public void setRecCrtDt(String recCrtDt) {
        this.recCrtDt = recCrtDt == null ? null : recCrtDt.trim();
    }

    public String getRecUpdDt() {
        return recUpdDt;
    }

    public void setRecUpdDt(String recUpdDt) {
        this.recUpdDt = recUpdDt == null ? null : recUpdDt.trim();
    }
}