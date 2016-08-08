package com.centerm.service.prom.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.menu.MenuVersionInfMapper;
import com.centerm.dao.prom.DiscountRuleInfMapper;
import com.centerm.dao.prom.DiscountTimeInfMapper;
import com.centerm.dao.sys.ParamInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.prom.DiscountRuleInf;
import com.centerm.model.prom.DiscountTimeInf;
import com.centerm.model.sys.ParamInf;
import com.centerm.service.prom.IDiscountConfigServiceImpl;
import com.centerm.service.sys.impl.SysLogService;

@Service("DiscountConfigService")
@Transactional
public class DiscountConfigServiceImpl implements IDiscountConfigServiceImpl{
	
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private DiscountTimeInfMapper discountTimeMapper;
	@Autowired
	private DiscountRuleInfMapper discountRuleMapper;
	@Autowired
	private ParamInfMapper paramMapper;
	@Autowired
	private MenuVersionInfMapper menuVersionMapper;
	@Autowired
	private SysLogService sysLogService;
	
	public List<DiscountTimeInf> getModeParamByMchntCd(String mchntCd){
		return discountTimeMapper.getModeParamByMchntCd(mchntCd);
	}
	
	public int del(int id){
		return discountTimeMapper.deleteByPrimaryKey(id);
	}
	
	public void del(DiscountTimeInf discountTime) {
		logger.info("====删除优惠单品开始:"+discountTime.getParam2());
		discountTimeMapper.deleteByMchntCdAndProductId(discountTime);
		discountRuleMapper.deleteByMchntCdAndProductId(discountTime);
		//日志
		menuVersionMapper.versionIncrement(discountTime.getMchntCd());//菜单版本自增
		sysLogService.add("DiscountConfigServiceImpl.del", 
				"tbl_bkms_discount_rule_config_inf, tbl_bkms_discount_time_config_inf",
				discountTime, SysLogService.DELETE);
		logger.info("====删除优惠单品结束:"+discountTime.getParam2());
	}
	
	public void update(DiscountTimeInf discountTime, List<DiscountRuleInf> discountRules){
		logger.info("====修改优惠单品开始:"+discountTime.getParam2());
		discountTimeMapper.updateByPrimaryKey(discountTime);
		if (discountRules != null && discountRules.size() > 0) {
			discountRuleMapper.updateByPrimaryKey(discountRules.get(0));
		} else {
			throw new BusinessException("修改失败！没有单品配置参数！");
		}
		//日志
		menuVersionMapper.versionIncrement(discountTime.getMchntCd());//菜单版本自增
		sysLogService.add("DiscountConfigServiceImpl.update", 
				"tbl_bkms_discount_rule_config_inf, tbl_bkms_discount_time_config_inf",
				discountTime, SysLogService.UPDATE);
		logger.info("====修改优惠结束:"+discountTime.getParam2());
	}

	public void add(DiscountTimeInf discountTime, List<DiscountRuleInf> discountRules) {
		logger.info("====添加优惠单品开始:"+discountTime.getParam2());
		discountTimeMapper.insert(discountTime);
		if (discountRules != null && discountRules.size() > 0) {
			discountRuleMapper.insert(discountRules.get(0));
		} else {
			throw new BusinessException("添加失败！没有单品配置参数！");
		}
		//日志
		menuVersionMapper.versionIncrement(discountTime.getMchntCd());//菜单版本自增
		sysLogService.add("DiscountConfigServiceImpl.add", 
				"tbl_bkms_discount_rule_config_inf, tbl_bkms_discount_time_config_inf",
				discountTime, SysLogService.INSERT);
		logger.info("====添加优惠结束:"+discountTime.getParam2());
	}
	
	public void save(ParamInf param, List<DiscountTimeInf> discountTimes) {
		logger.info("=====修改折扣开始:"+param.getMchntCd());
		paramMapper.updateByPrimaryKeySelective(param);
		//删除折扣一二旧配置
		discountRuleMapper.deleteByMchntCd(param.getMchntCd());
		discountTimeMapper.deleteByMchntCd(param.getMchntCd());
		//添加折扣一二新配置rule
		List<DiscountRuleInf> rules = new ArrayList<DiscountRuleInf>();
		for (DiscountTimeInf discountTime : discountTimes) {
			discountTime.setMchntCd(param.getMchntCd());
			rules.addAll(discountTime.getRules());
		}
		
		for (DiscountRuleInf discountRule : rules) {
			discountRule.setMchntCd(param.getMchntCd());
		}
		
		if (discountTimes != null && discountTimes.size() > 0) {
			discountTimeMapper.insertbatch(discountTimes);
		}
		if (rules != null && rules.size() > 0) {
			discountRuleMapper.insertbatch(rules);
		}
		//日志
		menuVersionMapper.versionIncrement(param.getMchntCd());//菜单版本自增
		sysLogService.add("DiscountConfigServiceImpl.save", 
				"tbl_bkms_mchnt_param_inf, tbl_bkms_discount_rule_config_inf, tbl_bkms_discount_time_config_inf",
				new Object[]{param, discountTimes}, SysLogService.UPDATE);
		logger.info("=====修改折扣结束:"+param.getMchntCd());
	}

	@Override
	public List<DiscountTimeInf> list(String mchntCd, Page page) {
		return discountTimeMapper.findDiscountOfMode3(mchntCd);
	}
}
