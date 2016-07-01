package com.centerm.service.mchnt.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.mchnt.OperatorInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.mchnt.OperatorInf;
import com.centerm.service.mchnt.IOperatorServiceImpl;
import com.centerm.service.sys.impl.SysLogService;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.MD5;

@Service("operatorService")
@Transactional
public class OperatorServiceImpl implements IOperatorServiceImpl{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private SysLogService sysLogService;
	
	public SysLogService getSysLogService() {
		return sysLogService;
	}
	@Autowired
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	private OperatorInfMapper operatorMapper;

	public OperatorInfMapper getOperatorMapper() {
		return operatorMapper;
	}
	@Autowired
	public void setOperatorMapper(OperatorInfMapper operatorMapper) {
		this.operatorMapper = operatorMapper;
	}
	
	public List<OperatorInf> list(OperatorInf operator, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(operator);
		map.put("page", page);
		return operatorMapper.query(map);
	}
	
	public void del(OperatorInf operator){
		logger.info("=====删除操作员开始:"+operator.getOperId());
		operatorMapper.deleteByPrimaryKey(operator);
		//日志
		sysLogService.add("OperatorServiceImpl.del", "tbl_bkms_oper_inf", operator, SysLogService.DELETE);
		logger.info("=====删除操作员开始:"+operator.getOperId());
	}
	
	public void add(OperatorInf operator){
		logger.info("=====添加操作员开始:"+operator.getOperId());
		//唯一性校验
		int num = operatorMapper.isIdExisted(operator);
		if (num > 0) {
			throw new BusinessException("操作员账号已被使用");
		}
		operatorMapper.insert(operator);
		//日志
		sysLogService.add("OperatorServiceImpl.add", "tbl_bkms_oper_inf", operator, SysLogService.UPDATE);
		logger.info("=====添加操作员结束:"+operator.getOperId());
	}
	
	public int update(OperatorInf operator){
		return operatorMapper.updateByPrimaryKeySelective(operator);
	}
	@Override
	public void resetPwd(OperatorInf operator, String newPwd) {
		logger.info("=====操作员密码重置开始:"+operator.getOperId());
		/*OperatorInf oper = operatorMapper.selectByPrimaryKey(operator);
		if(!(new MD5().getMD5Str(oldPwd)).equals(oper.getPasswd())) {
			throw new BusinessException("旧密码不正确");
		}
		if(oldPwd.equals(newPwd)) {
			throw new BusinessException("新旧密码不能相同");
		}*/
		operator.setPasswd(new MD5().getMD5Str(newPwd));
		operatorMapper.updateByPrimaryKeySelective(operator);
		logger.info("======操作员密码重置结束:"+operator.getOperId());
	}
}
