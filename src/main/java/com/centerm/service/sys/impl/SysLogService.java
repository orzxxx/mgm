package com.centerm.service.sys.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.centerm.base.Constant;
import com.centerm.dao.sys.SysLogInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.sys.LoginUser;
import com.centerm.model.sys.SysLogInf;
import com.centerm.utils.DateUtils;
import com.centerm.utils.StringUtils;
import com.google.gson.Gson;

@Service("sysLogService")
@Transactional
public class SysLogService{
	public static final int INSERT = 0;
	public static final int UPDATE = 1;
	public static final int DELETE = 2;
	public static final int SELECT = 3;
	
	private SysLogInfMapper sysLogMapper;

	private SysLogInfMapper getSysLogMapper() {
		return sysLogMapper;
	}
	@Autowired
	private void setSysLogMapper(SysLogInfMapper sysLogMapper) {
		this.sysLogMapper = sysLogMapper;
	}
	
	private int add(SysLogInf sysLog){
		return sysLogMapper.insert(sysLog);
	}
	
	public void add(String func, String table, Object param, int type){
		add(func, new String[]{table}, param, type, null);
	}
	
	public void add(String func, String table, Object param, int type, String remark){
		add(func, new String[]{table}, param, type, remark);
	}
	
	public void add(String func, String[] tables, Object param, int type){
		add(func, tables, param, type, null);
	}
	
	public void add(String func, String[] tables, Object param, int type, String remark){
		//从session中获取UserId
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest(); 
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser) session.getAttribute(Constant.LOGIN_USER);
		String userId = loginUser.getUserInfo().getUserId();
		
		add(userId, func, tables, param, type, remark);
	}
	
	public void add(String userId, String func, String table, Object param, int type){
		add(userId, func, new String[]{table}, param, type, null);
	}
	
	public void add(String userId, String func, String table, Object param, int type, String remark){
		add(userId, func, new String[]{table}, param, type, remark);
	}
	
	public void add(String userId, String func, String[] tables, Object param, int type){
		add(userId, func, tables, param, type, null);
	}
	
	public void add(String userId, String func, String[] tables, Object param, int type, String remark){
		SysLogInf sysLog = new SysLogInf();
		sysLog.setUuid(UUID.randomUUID().toString());
		sysLog.setUserId(userId);
		sysLog.setOperFunc(func);
		sysLog.setOperDt(DateUtils.getCurrentDate("yyyyMMddHHmmss"));
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
		
		if (!StringUtils.isNull(remark)){
			desc.append("备注:"+remark);
		}
		desc.append(operType+" "+table+"\r\n");
		desc.append("参数:"+jsonParam);
		
		sysLog.setOperDesc(desc.toString());
		
		sysLogMapper.insert(sysLog);
	}
	
	public void addInfo(String func, String info){
		//从session中获取UserId
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest(); 
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser) session.getAttribute(Constant.LOGIN_USER);
		String userId = loginUser.getUserInfo().getUserId();
		
		addInfo(userId, func, info);
	}
	
	public void addInfo(String userId, String func, String info){
		SysLogInf sysLog = new SysLogInf();
		sysLog.setUuid(UUID.randomUUID().toString());
		sysLog.setUserId(userId);
		sysLog.setOperFunc(func);
		sysLog.setOperDt(DateUtils.getCurrentDate("yyyyMMddHHmmss"));
		sysLog.setOperDesc(info);
		
		sysLogMapper.insert(sysLog);
	}
}
