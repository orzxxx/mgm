package com.centerm.service.mchnt.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.mchnt.FrchseAuditInfMapper;
import com.centerm.dao.mchnt.FrchseInfMapper;
import com.centerm.dao.mchnt.FrchseMchntMapInfMapper;
import com.centerm.model.mchnt.FrchseAuditInf;
import com.centerm.model.mchnt.FrchseInf;
import com.centerm.model.mchnt.FrchseMchntMapInf;
import com.centerm.service.mchnt.IFrchseAuditService;
import com.centerm.service.sys.impl.SysLogService;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.DateUtils;

@Service("frchseAuditService")
@Transactional
public class FrchseAuditServiceImpl implements IFrchseAuditService{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private SysLogService sysLogService;
	
	public SysLogService getSysLogService() {
		return sysLogService;
	}
	@Autowired
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	private FrchseAuditInfMapper frchseAuditMapper;
	
	private FrchseMchntMapInfMapper frchseMchntMapMapper; 
	
	private FrchseInfMapper frchseMapper;
	
	public FrchseInfMapper getFrchseMapper() {
		return frchseMapper;
	}
	@Autowired
	public void setFrchseMapper(FrchseInfMapper frchseMapper) {
		this.frchseMapper = frchseMapper;
	}
	
	public FrchseMchntMapInfMapper getFrchseMchntMapMapper() {
		return frchseMchntMapMapper;
	}
	@Autowired
	public void setFrchseMchntMapMapper(FrchseMchntMapInfMapper frchseMchntMapMapper) {
		this.frchseMchntMapMapper = frchseMchntMapMapper;
	}

	public FrchseAuditInfMapper getFrchseAuditMapper() {
		return frchseAuditMapper;
	}
	@Autowired
	public void setFrchseAuditMapper(FrchseAuditInfMapper frchseAuditMapper) {
		this.frchseAuditMapper = frchseAuditMapper;
	}
	
	public List<FrchseAuditInf> list(FrchseAuditInf frchseAudit, Page page) throws Exception{
		frchseAudit.setAuditStatus(2);
		Map<String,Object> map = BeanUtil.bean2Map(frchseAudit);
		map.put("page", page);
		return frchseAuditMapper.query(map);
	}
	
	public int del(int id){
		return frchseAuditMapper.deleteByPrimaryKey(id);
	}
	
	public void add(FrchseAuditInf frchseAudit){
		logger.info("=====关联申请开始:"+frchseAudit.getFrchseCd());
		//先删除
		frchseAuditMapper.deleteByMchntCd(frchseAudit.getMchntCd());
		frchseAudit.setUuid(UUID.randomUUID().toString());
		frchseAudit.setSubmitTime(DateUtils.getCurrentDate());
		frchseAudit.setAuditStatus(2);
		frchseAuditMapper.insert(frchseAudit);
		//日志
		sysLogService.addInfo("FrchseAuditServiceImpl.add", "提交关联申请:"+frchseAudit.getFrchseCd());
		logger.info("=====关联申请结束:"+frchseAudit.getFrchseCd());
	}
	
	public void update(FrchseAuditInf frchseAudit){
		logger.info("=====关联审核开始:"+frchseAudit.getMchntCd());
		//查找总部信息
		FrchseInf frchse = frchseMapper.selectByPrimaryKey(frchseAudit.getFrchseCd());
		//修改关联审核
		frchseAudit.setAuditRole(frchse.getFrchseName());
		frchseAudit.setAuditTime(DateUtils.getCurrentDate());
		frchseAuditMapper.updateByPrimaryKeySelective(frchseAudit);
		if (frchseAudit.getAuditStatus() == 1) {
			FrchseMchntMapInf fmmi = new FrchseMchntMapInf();
			fmmi.setFrchseCd(frchseAudit.getFrchseCd());
			fmmi.setMchntCd(frchseAudit.getMchntCd());
			fmmi.setStatus(0);
			fmmi.setUuid(UUID.randomUUID().toString());
			frchseMchntMapMapper.insert(fmmi);
			frchseAuditMapper.updateByPrimaryKeySelective(frchseAudit);
		}
		//日志
		sysLogService.add("FrchseAuditServiceImpl.update", "tbl_bkms_frchse_mchnt_audit_inf", frchseAudit, SysLogService.UPDATE);
		if (frchseAudit.getAuditStatus() == 1) {
			logger.info("=====关联审核通过");
		} else {
			logger.info("=====关联审核不通过");
		}
		logger.info("=====关联审核结束:"+frchseAudit.getMchntCd());
	}
	@Override
	public FrchseAuditInf getByMchntCd(String mchntCd) {
		return frchseAuditMapper.getByMchntCd(mchntCd);
	}
}
