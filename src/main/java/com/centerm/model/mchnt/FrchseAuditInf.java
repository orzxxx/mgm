package com.centerm.model.mchnt;

public class FrchseAuditInf {
    private String uuid;

    private String frchseCd;

    private String mchntCd;

    private Integer auditStatus;

    private String auditRole;

    private String auditInf;

    private String auditTime;

    private String submitTime;
    
    private FrchseInf frchse;
    
    private MchntInf mchnt;
    
    public FrchseInf getFrchse() {
		return frchse;
	}

	public void setFrchse(FrchseInf frchse) {
		this.frchse = frchse;
	}

	public MchntInf getMchnt() {
		return mchnt;
	}

	public void setMchnt(MchntInf mchnt) {
		this.mchnt = mchnt;
	}

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

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditRole() {
        return auditRole;
    }

    public void setAuditRole(String auditRole) {
        this.auditRole = auditRole == null ? null : auditRole.trim();
    }

    public String getAuditInf() {
        return auditInf;
    }

    public void setAuditInf(String auditInf) {
        this.auditInf = auditInf == null ? null : auditInf.trim();
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime == null ? null : auditTime.trim();
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime == null ? null : submitTime.trim();
    }
}