package com.centerm.service.prom.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.prom.PromotionInfMapper;
import com.centerm.dao.sys.ParamInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.prom.PromotionInf;
import com.centerm.service.prom.IPromotionServiceImpl;
import com.centerm.utils.BeanUtil;

@Service("promotionService")
@Transactional
public class PromotionServiceImpl implements IPromotionServiceImpl{

	private PromotionInfMapper promotionMapper;
	
	public PromotionInfMapper getPromotionMapper() {
		return promotionMapper;
	}
	@Autowired
	public void setPromotionMapper(PromotionInfMapper promotionMapper) {
		this.promotionMapper = promotionMapper;
	}
	
	public List<PromotionInf> list(PromotionInf promotion, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(promotion);
		map.put("page", page);
		return promotionMapper.findPromotionProducts(map);
	}
	
	public int del(String id){
		return promotionMapper.deleteByPrimaryKey(id);
	}
	
	public int add(PromotionInf promotion){
		//
		PromotionInf prop = promotionMapper.selectByPrimaryKey(promotion.getProductId());
		if (prop != null) {
			throw new BusinessException("该菜品已添加为促销菜品");
		}
		return promotionMapper.insert(promotion);
	}
	
	public int update(PromotionInf promotion){
		return promotionMapper.updateByPrimaryKeySelective(promotion);
	}
}
