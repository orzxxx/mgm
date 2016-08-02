package com.centerm.service.prom.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.menu.MenuVersionInfMapper;
import com.centerm.dao.prom.DiscountRuleInfMapper;
import com.centerm.dao.prom.DiscountTimeInfMapper;
import com.centerm.dao.sys.ParamInfMapper;
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
	
	public int update(DiscountTimeInf DiscountTime){
		return discountTimeMapper.updateByPrimaryKeySelective(DiscountTime);
	}

	public void add(ParamInf param, List<DiscountTimeInf> discountTimes) {
		logger.info("=====修改折扣开始:"+param.getMchntCd());
		paramMapper.updateByPrimaryKeySelective(param);
		//删除旧配置
		discountRuleMapper.deleteByMchntCd(param.getMchntCd());
		discountTimeMapper.deleteByMchntCd(param.getMchntCd());
		//添加新配置
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
		sysLogService.add("DiscountConfigServiceImpl.update", 
				"tbl_bkms_mchnt_param_inf, tbl_bkms_discount_rule_config_inf, tbl_bkms_discount_time_config_inf",
				new Object[]{param, discountTimes}, SysLogService.UPDATE);
		logger.info("=====修改折扣结束:"+param.getMchntCd());
	}
}
