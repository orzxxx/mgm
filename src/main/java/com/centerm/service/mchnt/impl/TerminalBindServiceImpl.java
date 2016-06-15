package com.centerm.service.mchnt.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.mchnt.TerminalBindInfMapper;
import com.centerm.model.mchnt.TerminalBindInf;
import com.centerm.service.mchnt.ITerminalBindServiceImpl;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.StringUtils;

@Service("terminalBindService")
@Transactional
public class TerminalBindServiceImpl implements ITerminalBindServiceImpl{

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
		Map<String,Object> map = BeanUtil.bean2Map(terminalBind);
		map.put("page", page);
		return terminalBindMapper.query(map);
	}
	
	public int del(TerminalBindInf terminalBind){
		return terminalBindMapper.deleteByPrimaryKey(terminalBind);
	}
	
	public int add(TerminalBindInf terminalBind){
		return terminalBindMapper.insert(terminalBind);
	}
	
	public int update(TerminalBindInf terminalBind){
		return terminalBindMapper.updateByPrimaryKeySelective(terminalBind);
	}
}
