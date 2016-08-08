package com.centerm.service.mchnt.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.mchnt.TerminalBindInfMapper;
import com.centerm.model.mchnt.TerminalBindInf;
import com.centerm.service.mchnt.ITerminalBindService;
import com.centerm.service.sys.impl.SysLogService;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.StringUtils;

@Service("terminalBindService")
@Transactional
public class TerminalBindServiceImpl implements ITerminalBindService{

	private Logger logger = Logger.getLogger(this.getClass());
	
	private SysLogService sysLogService;
	
	public SysLogService getSysLogService() {
		return sysLogService;
	}
	@Autowired
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}
	
	private TerminalBindInfMapper terminalBindMapper;

	public TerminalBindInfMapper getTerminalBindMapper() {
		return terminalBindMapper;
	}
	@Autowired
	public void setTerminalBindMapper(TerminalBindInfMapper terminalBindMapper) {
		this.terminalBindMapper = terminalBindMapper;
	}
	
	public List<TerminalBindInf> list(TerminalBindInf terminalBind, Page page) throws Exception{
		//模糊查询
		if (!StringUtils.isNull(terminalBind.getMchntCd())) {
			terminalBind.setMchntCd("%"+terminalBind.getMchntCd()+"%");
		}
		if (!StringUtils.isNull(terminalBind.getTerminalCd())) {
			terminalBind.setTerminalCd("%"+terminalBind.getTerminalCd()+"%");
		}
		if (!StringUtils.isNull(terminalBind.getTerminalSn())) {
			terminalBind.setTerminalSn("%"+terminalBind.getTerminalSn()+"%");
		}
		if (!StringUtils.isNull(terminalBind.getUserId())) {
			terminalBind.setUserId("%"+terminalBind.getUserId()+"%");
		}
		Map<String,Object> map = BeanUtil.bean2Map(terminalBind);
		map.put("page", page);
		return terminalBindMapper.query(map);
	}
	
	public void del(TerminalBindInf terminalBind){
		logger.info("=====终端解绑开始:"+terminalBind.getTerminalSn());
		terminalBindMapper.deleteByPrimaryKey(terminalBind);
		//日志
		sysLogService.add("TerminalBindServiceImpl.del", "tbl_bkms_terminal_bind_inf", terminalBind, SysLogService.DELETE);
		logger.info("=====终端解绑结束:"+terminalBind.getTerminalSn());
	}
	
	public int add(TerminalBindInf terminalBind){
		return terminalBindMapper.insert(terminalBind);
	}
	
	public int update(TerminalBindInf terminalBind){
		return terminalBindMapper.updateByPrimaryKeySelective(terminalBind);
	}
}
