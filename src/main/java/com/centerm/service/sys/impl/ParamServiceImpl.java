package com.centerm.service.sys.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.sys.ParamInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.sys.ParamInf;
import com.centerm.service.sys.IParamServiceImpl;
import com.centerm.utils.BeanUtil;

@Service("paramService")
@Transactional
public class ParamServiceImpl implements IParamServiceImpl{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private SysLogService sysLogService;
	
	public SysLogService getSysLogService() {
		return sysLogService;
	}
	@Autowired
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	private ParamInfMapper paramMapper;

	public ParamInfMapper getParamMapper() {
		return paramMapper;
	}
	@Autowired
	public void setParamMapper(ParamInfMapper paramMapper) {
		this.paramMapper = paramMapper;
	}
	
	public List<ParamInf> list(ParamInf param, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(param);
		map.put("page", page);
		return paramMapper.query(map);
	}
	
	public int del(int id){
		return paramMapper.deleteByPrimaryKey(id);
	}
	
	public ParamInf get(ParamInf param) throws Exception{
		ParamInf result = paramMapper.getParam(param);
		if (result == null) {
			//未配置则添加
			if (param.getParam().equals("discount_rate")) {
				param.setData("10|20160101|20160101");
			} else if (param.getParam().equals("packing_box_price")) {
				param.setData("0");
			} else {
				throw new BusinessException("未知的配置参数");
			}
			
			param.setMchntCd(param.getMchntCd());
			param.setUuid(UUID.randomUUID().toString());
			paramMapper.insert(param);
		}
		return paramMapper.getParam(param);
	}
	
	public int add(ParamInf param){
		return paramMapper.insert(param);
	}
	
	public void update(ParamInf param){
		logger.info("=====修改参数开始:"+param.getMchntCd());
		paramMapper.updateByPrimaryKeySelective(param);
		//日志
		sysLogService.add("ParamServiceImpl.update", "tbl_bkms_mchnt_param_inf", param, SysLogService.UPDATE);
		logger.info("=====修改参数结束:"+param.getMchntCd());
	}
}
