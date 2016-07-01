package com.centerm.service.prom.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.prom.PromotionInfMapper;
import com.centerm.dao.sys.ParamInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.prom.PromotionInf;
import com.centerm.service.prom.IPromotionServiceImpl;
import com.centerm.service.sys.impl.SysLogService;
import com.centerm.utils.BeanUtil;

@Service("promotionService")
@Transactional
public class PromotionServiceImpl implements IPromotionServiceImpl{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private SysLogService sysLogService;
	
	public SysLogService getSysLogService() {
		return sysLogService;
	}
	@Autowired
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

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
	
	public void del(String id){
		logger.info("=====删除活动单品开始:"+id);
		promotionMapper.deleteByPrimaryKey(id);
		//日志
		sysLogService.add("PromotionServiceImpl.del", "tbl_bkms_promotion_inf", id, SysLogService.DELETE);
		logger.info("=====删除活动单品结束:"+id);
	}
	
	public void add(PromotionInf promotion){
		logger.info("=====添加活动单品开始:"+promotion.getProductId());
		PromotionInf prop = promotionMapper.selectByPrimaryKey(promotion.getProductId());
		if (prop != null) {
			logger.info("=====该菜品已添加为促销菜品:"+promotion.getProductId());
			throw new BusinessException("该菜品已添加为促销菜品");
		}
		promotionMapper.insert(promotion);
		//日志
		sysLogService.add("PromotionServiceImpl.add", "tbl_bkms_promotion_inf", promotion, SysLogService.INSERT);
		logger.info("=====添加活动单品结束:"+promotion.getProductId());
	}
	
	public void update(PromotionInf promotion){
		logger.info("=====更新活动单品开始:"+promotion.getProductId());
		promotionMapper.updateByPrimaryKeySelective(promotion);
		//日志
		sysLogService.add("PromotionServiceImpl.update", "tbl_bkms_promotion_inf", promotion, SysLogService.UPDATE);
		logger.info("=====更新活动单品结束:"+promotion.getProductId());
	}
}
