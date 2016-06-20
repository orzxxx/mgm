package com.centerm.model.mchnt;

public class MchntAuditInf {
    private String mchntCd;

    private String licenseFront;

    private String licenseBack;

    private String idCardFront;

    private String idCardBack;
    
    private String storePhoto;

	private Integer auditStatus;

    private String auditRole;

    private String auditInf;

    private String auditTime;

    private String submitTime;
    
    private MchntInf mchnt;
    
    public String getStorePhoto() {
		return storePhoto;
	}

	public void setStorePhoto(String storePhoto) {
		this.storePhoto = storePhoto;
	}

    public MchntInf getMchnt() {
		return mchnt;
	}

	public void setMchnt(MchntInf mchnt) {
		this.mchnt = mchnt;
	}

	public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd == null ? null : mchntCd.trim();
    }

    public String getLicenseFront() {
        return licenseFront;
    }

    public void setLicenseFront(String licenseFront) {
        this.licenseFront = licenseFront == null ? null : licenseFront.trim();
    }

    public String getLicenseBack() {
        return licenseBack;
    }

    public void setLicenseBack(String licenseBack) {
        this.licenseBack = licenseBack == null ? null : licenseBack.trim();
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront == null ? null : idCardFront.trim();
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack == null ? null : idCardBack.trim();
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