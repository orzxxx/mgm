package com.centerm.service.prom;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.prom.DiscountRuleInf;
import com.centerm.model.prom.DiscountTimeInf;
import com.centerm.model.sys.ParamInf;

public interface IDiscountConfigService {
	public int del(int id);
	
	public void update(DiscountTimeInf DiscountTime, List<DiscountRuleInf> parseArray);
	
	public List<DiscountTimeInf> getModeParamByMchntCd(String mchntCd);

	public void save(ParamInf param, List<DiscountTimeInf> parseArray);

	public void add(DiscountTimeInf discountTime, List<DiscountRuleInf> parseArray);

	public List<DiscountTimeInf> list(String mchntCd, Page page);

	public void del(DiscountTimeInf discountTime);
}