package com.centerm.dao.prom;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.prom.DiscountTimeInf;

public interface DiscountTimeInfMapper extends BaseMapper<DiscountTimeInfMapper>{
	public List<DiscountTimeInf> getModeParamByMchntCd(String mchntCd);
	
	public void insertbatch(List<DiscountTimeInf> discountTimes);
	
	public void deleteByMchntCd(String mchntCd);

	public void deleteByMchntCdAndProductId(DiscountTimeInf discountTime);

	public List<DiscountTimeInf> findDiscountOfMode3(String mchntCd);
}