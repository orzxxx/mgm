package com.centerm.service.prom;

import java.util.List;

import com.centerm.model.prom.DiscountTimeInf;
import com.centerm.model.sys.ParamInf;

public interface IDiscountConfigServiceImpl {
	public int del(int id);
	
	public int update(DiscountTimeInf DiscountTime);
	
	public List<DiscountTimeInf> getModeParamByMchntCd(String mchntCd);

	public void add(ParamInf param, List<DiscountTimeInf> parseArray);
}