package com.centerm.dao.prom;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.prom.DiscountRuleInf;

public interface DiscountRuleInfMapper extends BaseMapper<DiscountRuleInfMapper>{
	public void insertbatch(List<DiscountRuleInf> discountRules);
	
	public void deleteByMchntCd(String mchntCd);
}