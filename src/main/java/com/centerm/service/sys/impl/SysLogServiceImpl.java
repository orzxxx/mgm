package com.centerm.service.sys.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.centerm.base.Constant;
import com.centerm.base.Page;
import com.centerm.dao.sys.SysLogInfMapper;
import com.centerm.model.sys.LoginUser;
import com.centerm.model.sys.SysLogInf;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.DateUtils;

@Service("sysLogService")
@Transactional
public class SysLogServiceImpl{

	private SysLogInfMapper sysLogMapper;

	public SysLogInfMapper getSysLogMapper() {
		return sysLogMapper;
	}
	@Autowired
	public void setSysLogMapper(SysLogInfMapper sysLogMapper) {
		this.sysLogMapper = sysLogMapper;
	}
	
	public int add(SysLogInf sysLog){
		return sysLogMapper.insert(sysLog);
	}
	
	public void add(String userId, String func){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest(); 
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser) session.getAttribute(Constant.LOGIN_USER);
		loginUser.getUserInfo().getUserId();
		SysLogInf sysLog = new SysLogInf();
		sysLog.setUserId(userId);
		sysLog.setOperFunc(func);
		//sysLog.setUuid();
		sysLog.setOperDt(DateUtils.getCurrentDate("yyyy-DD-mm HH:mm:ss"));
		
	}
	
}
