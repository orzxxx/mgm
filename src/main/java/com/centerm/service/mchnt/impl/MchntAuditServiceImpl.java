package com.centerm.service.mchnt.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
import com.centerm.utils.HttpClientUtil;

@Service("mchntAuditService")
@Transactional
public class MchntAuditServiceImpl implements IMchntAuditServiceImpl{
	
	private Logger logger = Logger.getLogger(this.getClass());

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
			//短信提醒
			String result = sendSms("15394538563", "您的验证码为:123456");
			System.out.println(result);
		}
	}
	//发送短信
	private String sendSms(String phoneNum, String content){
		logger.info("【send sms phone】"+phoneNum+" content="+content);
		Map<String,String> paraMap=new HashMap<String,String>();
		paraMap.put("SpCode", "103906");
		paraMap.put("LoginName", "fj_st");
		paraMap.put("Password", "cpay2016");
		paraMap.put("MessageContent",content );
		paraMap.put("UserNumber", phoneNum);
		paraMap.put("SerialNumber", System.currentTimeMillis()+"");
		paraMap.put("ScheduleTime", "");
		paraMap.put("f", 1+"");
		String result=HttpClientUtil.doPost("https://smsapi.ums86.com:9600/sms/Api/Send.do", paraMap,"GBK");
		logger.info("【sms result】"+result);
		return result;
	}
}
