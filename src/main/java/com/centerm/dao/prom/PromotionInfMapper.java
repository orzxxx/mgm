package com.centerm.dao.prom;

import java.util.List;
import java.util.Map;

import com.centerm.base.BaseMapper;
import com.centerm.model.prom.PromotionInf;

public interface PromotionInfMapper extends BaseMapper<PromotionInf>{

	public List<PromotionInf> findPromotionProducts(Map map);
}