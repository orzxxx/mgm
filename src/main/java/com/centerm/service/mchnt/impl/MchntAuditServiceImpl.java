package com.centerm.service.mchnt.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.mchnt.MchntAuditInfMapper;
import com.centerm.dao.mchnt.MchntInfMapper;
import com.centerm.dao.sys.UserInfMapper;
import com.centerm.model.mchnt.MchntAuditInf;
import com.centerm.model.mchnt.MchntInf;
import com.centerm.model.sys.UserInf;
import com.centerm.service.mchnt.IMchntAuditServiceImpl;
import com.centerm.utils.BeanUtil;

@Service("mchntAuditService")
@Transactional
public class MchntAuditServiceImpl implements IMchntAuditServiceImpl{

	private MchntAuditInfMapper mchntAuditMapper;
	private MchntInfMapper mchntMapper;
	private UserInfMapper userMapper;

	public MchntInfMapper getMchntMapper() {
		return mchntMapper;
	}
	@Autowired
	public void setMchntMapper(MchntInfMapper mchntMapper) {
		this.mchntMapper = mchntMapper;
	}
	public UserInfMapper getUserMapper() {
		return userMapper;
	}
	@Autowired
	public void setUserMapper(UserInfMapper userMapper) {
		this.userMapper = userMapper;
	}
	public MchntAuditInfMapper getMchntAuditMapper() {
		return mchntAuditMapper;
	}
	@Autowired
	public void setMchntAuditMapper(MchntAuditInfMapper mchntAuditMapper) {
		this.mchntAuditMapper = mchntAuditMapper;
	}
	
	public List<MchntAuditInf> list(MchntAuditInf mchntAudit, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(mchntAudit);
		map.put("page", page);
		return mchntAuditMapper.query(map);
	}
	
	public int del(int id){
		return mchntAuditMapper.deleteByPrimaryKey(id);
	}
	
	public int add(MchntAuditInf mchntAudit){
		return mchntAuditMapper.insert(mchntAudit);
	}
	
	public int update(MchntAuditInf mchntAudit){
		return mchntAuditMapper.updateByPrimaryKeySelective(mchntAudit);
	}
	@Override
	public MchntAuditInf get(String mchntCd) {
		return mchntAuditMapper.selectByPrimaryKey(mchntCd);
	}
	@Override
	public void audit(MchntAuditInf mchntAudit) {
		mchntAuditMapper.updateByPrimaryKeySelective(mchntAudit);
		//审核通过赋予权限
		if (mchntAudit.getAuditStatus() == 1) {
			UserInf user = userMapper.findByMchntCd(mchntAudit.getMchntCd());
			user.setRole("merchant");
			userMapper.updateByPrimaryKey(user);
			MchntInf mchnt = mchntMapper.selectByUserId(user.getUserId());
			mchnt.setStatus(0);
			mchntMapper.updateByPrimaryKey(mchnt);
		}
	}
}
