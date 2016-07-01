package com.centerm.service.prom;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.prom.PromotionInf;

public interface IPromotionServiceImpl {
	public List<PromotionInf> list(PromotionInf promotion, Page page) throws Exception;
	
	public void del(String id);
	
	public void add(PromotionInf promotion);
	
	public void update(PromotionInf promotion);
}