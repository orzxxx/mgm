package com.centerm.model.prom;

import java.util.List;

public class DiscountTimeInf {
    private Integer id;

    private String mchntCd;

    private Integer discountMode;

    private String param1;

    private String param2;
    
    private List<DiscountRuleInf> rules;

    public List<DiscountRuleInf> getRules() {
		return rules;
	}

	public void setRules(List<DiscountRuleInf> rules) {
		this.rules = rules;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd == null ? null : mchntCd.trim();
    }

    public Integer getDiscountMode() {
        return discountMode;
    }

    public void setDiscountMode(Integer discountMode) {
        this.discountMode = discountMode;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1 == null ? null : param1.trim();
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2 == null ? null : param2.trim();
    }
}