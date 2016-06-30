package com.centerm.model.sys;

public class SysLogInf {
    private String uuid;

    private String userId;

    private String operDt;

    private String operFunc;

    private String operDesc;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getOperDt() {
        return operDt;
    }

    public void setOperDt(String operDt) {
        this.operDt = operDt == null ? null : operDt.trim();
    }

    public String getOperFunc() {
        return operFunc;
    }

    public void setOperFunc(String operFunc) {
        this.operFunc = operFunc == null ? null : operFunc.trim();
    }

    public String getOperDesc() {
        return operDesc;
    }

    public void setOperDesc(String operDesc) {
        this.operDesc = operDesc == null ? null : operDesc.trim();
    }
}