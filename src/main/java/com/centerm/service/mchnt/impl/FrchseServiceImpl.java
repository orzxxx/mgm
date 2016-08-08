package com.centerm.service.mchnt.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.mchnt.FrchseInfMapper;
import com.centerm.model.mchnt.FrchseInf;
import com.centerm.service.mchnt.IFrchseService;
import com.centerm.service.sys.impl.SysLogService;
import com.centerm.utils.BeanUtil;

@Service("frchseService")
@Transactional
public class FrchseServiceImpl implements IFrchseService{

	private FrchseInfMapper frchseMapper;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private SysLogService sysLogService;
	
	public SysLogService getSysLogService() {
		return sysLogService;
	}
	@Autowired
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}
	
	public FrchseInfMapper getFrchseMapper() {
		return frchseMapper;
	}
	@Autowired
	public void setFrchseMapper(FrchseInfMapper frchseMapper) {
		this.frchseMapper = frchseMapper;
	}
	
	public List<FrchseInf> list(FrchseInf frchse, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(frchse);
		map.put("page", page);
		return frchseMapper.query(map);
	}
	public FrchseInf get(String mchntCd){
		return frchseMapper.selectByPrimaryKey(mchntCd);
	}
	public int del(int id){
		return frchseMapper.deleteByPrimaryKey(id);
	}
	
	public int add(FrchseInf frchse){
		return frchseMapper.insert(frchse);
	}
	
	public void update(FrchseInf frchse){
		logger.info("=====更新总部信息开始:"+frchse.getFrchseCd());
		frchseMapper.updateByPrimaryKeySelective(frchse);
		//日志
		sysLogService.add("FrchseServiceImpl.update", "tbl_bkms_frchse_inf", frchse, SysLogService.UPDATE);
		logger.info("=====更新总部信息结束:"+frchse.getFrchseCd());
	}
}
