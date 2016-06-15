package com.centerm.model.sys;

public class RoleInf {
    private String roleId;

    private String roleCd;

    private String roleName;

    private String remark;

    private String recCrtDt;

    private String recUpdDt;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getRoleCd() {
        return roleCd;
    }

    public void setRoleCd(String roleCd) {
        this.roleCd = roleCd == null ? null : roleCd.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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