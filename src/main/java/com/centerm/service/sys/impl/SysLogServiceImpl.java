package com.centerm.service.sys.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.centerm.base.Constant;
import com.centerm.base.Page;
import com.centerm.dao.sys.SysLogInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.sys.LoginUser;
import com.centerm.model.sys.SysLogInf;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.DateUtils;
import com.centerm.utils.StringUtils;
import com.google.gson.Gson;

@Service("sysLogService")
@Transactional
public class SysLogServiceImpl{
	private final int INSERT = 0;
	private final int UPDATE = 1;
	private final int DELETE = 2;
	private final int SELECT = 3;
	
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
	
	public void add(String func, String table, Object param, int type){
		add(func, new String[]{table}, param, type);
	}
	
	public void add(String func, String[] tables, Object param, int type){
		//从session中获取UserId
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest(); 
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser) session.getAttribute(Constant.LOGIN_USER);
		String userId = loginUser.getUserInfo().getUserId();
		
		add(userId, func, tables, param, type);
	}
	
	public void add(String userId, String func, String table, Object param, int type){
		add(userId, func, new String[]{table}, param, type);
	}
	
	public void add(String userId, String func, String[] tables, Object param, int type){
		SysLogInf sysLog = new SysLogInf();
		sysLog.setUuid(UUID.randomUUID().toString());
		sysLog.setUserId(userId);
		sysLog.setOperFunc(func);
		sysLog.setOperDt(DateUtils.getCurrentDate("yyyy-DD-mm HH:mm:ss"));
		//详细信息
		StringBuffer desc =  new StringBuffer();
		String operType = "";
		if (type == this.INSERT) {
			operType = "插入";
		} else if(type == this.UPDATE) {
			operType = "更新";
		} else if(type == this.DELETE) {
			operType = "删除";
		} else if(type == this.SELECT) {
			operType = "查询";
		} else {
			throw new BusinessException("添加操作日志失败, 非法的操作类型");
		}
		String table = StringUtils.join(tables, ',');
		String jsonParam = new Gson().toJson(param);
		
		desc.append("");
	}
	
}
