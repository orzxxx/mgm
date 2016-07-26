package com.centerm.model.trade;

import java.math.BigDecimal;
import java.util.List;

public class OrderJourInf {
    private String orderNo;

    private String pseq;

    private String termTransTrc;

    private String mchntCd;
    
    private String mchntName;

	private String terminalCd;

    private String operId;

    private String termDatetime;

    private String transtime;

    private String transdate;

    private String datetime;

    private BigDecimal stdtrnsamt;

    private Integer totalnum;

    private String orderDetail;

    private String payNo;

    private String payTp;

    private String chnlSeq;

    private Integer trnsflag;

    private String setdate;

    private String trnsdatetime;

    private String stdrspcod;

    private String printInfo;

    private String chnlMchntcd;

    private String chnlTermcd;

    private String batchno;

    private String chnlSystraceno;

    private String authcode;

    private String orderidScan;
    
    private String minTransdate;
    
    private String maxTransdate;
    
    private BigDecimal discount;
    
    public String getMchntName() {
		return mchntName;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
    
    public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	private List<OrderDetailInf> details;

    public String getMinTransdate() {
		return minTransdate;
	}

	public void setMinTransdate(String minTransdate) {
		this.minTransdate = minTransdate;
	}

	public String getMaxTransdate() {
		return maxTransdate;
	}

	public void setMaxTransdate(String maxTransdate) {
		this.maxTransdate = maxTransdate;
	}

	public List<OrderDetailInf> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetailInf> details) {
		this.details = details;
	}

	public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getPseq() {
        return pseq;
    }

    public void setPseq(String pseq) {
        this.pseq = pseq == null ? null : pseq.trim();
    }

    public String getTermTransTrc() {
        return termTransTrc;
    }

    public void setTermTransTrc(String termTransTrc) {
        this.termTransTrc = termTransTrc == null ? null : termTransTrc.trim();
    }

    public String getMchntCd() {
		return mchntCd;
	}

	public void setMchntCd(String mchntCd) {
		this.mchntCd = mchntCd;
	}

	public String getTerminalCd() {
		return terminalCd;
	}

	public void setTerminalCd(String terminalCd) {
		this.terminalCd = terminalCd;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    public String getTermDatetime() {
        return termDatetime;
    }

    public void setTermDatetime(String termDatetime) {
        this.termDatetime = termDatetime == null ? null : termDatetime.trim();
    }

    public String getTranstime() {
        return transtime;
    }

    public void setTranstime(String transtime) {
        this.transtime = transtime == null ? null : transtime.trim();
    }

    public String getTransdate() {
        return transdate;
    }

    public void setTransdate(String transdate) {
        this.transdate = transdate == null ? null : transdate.trim();
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime == null ? null : datetime.trim();
    }

    public BigDecimal getStdtrnsamt() {
        return stdtrnsamt;
    }

    public void setStdtrnsamt(BigDecimal stdtrnsamt) {
        this.stdtrnsamt = stdtrnsamt;
    }

    public Integer getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(Integer totalnum) {
        this.totalnum = totalnum;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail == null ? null : orderDetail.trim();
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo == null ? null : payNo.trim();
    }

    public String getPayTp() {
        return payTp;
    }

    public void setPayTp(String payTp) {
        this.payTp = payTp == null ? null : payTp.trim();
    }

    public String getChnlSeq() {
        return chnlSeq;
    }

    public void setChnlSeq(String chnlSeq) {
        this.chnlSeq = chnlSeq == null ? null : chnlSeq.trim();
    }

    public Integer getTrnsflag() {
        return trnsflag;
    }

    public void setTrnsflag(Integer trnsflag) {
        this.trnsflag = trnsflag;
    }

    public String getSetdate() {
        return setdate;
    }

    public void setSetdate(String setdate) {
        this.setdate = setdate == null ? null : setdate.trim();
    }

    public String getTrnsdatetime() {
        return trnsdatetime;
    }

    public void setTrnsdatetime(String trnsdatetime) {
        this.trnsdatetime = trnsdatetime == null ? null : trnsdatetime.trim();
    }

    public String getStdrspcod() {
        return stdrspcod;
    }

    public void setStdrspcod(String stdrspcod) {
        this.stdrspcod = stdrspcod == null ? null : stdrspcod.trim();
    }

    public String getPrintInfo() {
        return printInfo;
    }

    public void setPrintInfo(String printInfo) {
        this.printInfo = printInfo == null ? null : printInfo.trim();
    }

    public String getChnlMchntcd() {
        return chnlMchntcd;
    }

    public void setChnlMchntcd(String chnlMchntcd) {
        this.chnlMchntcd = chnlMchntcd == null ? null : chnlMchntcd.trim();
    }

    public String getChnlTermcd() {
        return chnlTermcd;
    }

    public void setChnlTermcd(String chnlTermcd) {
        this.chnlTermcd = chnlTermcd == null ? null : chnlTermcd.trim();
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno == null ? null : batchno.trim();
    }

    public String getChnlSystraceno() {
        return chnlSystraceno;
    }

    public void setChnlSystraceno(String chnlSystraceno) {
        this.chnlSystraceno = chnlSystraceno == null ? null : chnlSystraceno.trim();
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode == null ? null : authcode.trim();
    }

    public String getOrderidScan() {
        return orderidScan;
    }

    public void setOrderidScan(String orderidScan) {
        this.orderidScan = orderidScan == null ? null : orderidScan.trim();
    }
}