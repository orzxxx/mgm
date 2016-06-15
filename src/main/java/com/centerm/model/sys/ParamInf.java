package com.centerm.model.sys;

public class ParamInf {
    private String uuid;

    private String mchntCd;

    private String param;

    private String data;
    
    public ParamInf(){
    	
    }
    
    public ParamInf(String mchntCd, String param, String data) {
		super();
		this.mchntCd = mchntCd;
		this.param = param;
		this.data = data;
	}

	public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd == null ? null : mchntCd.trim();
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}