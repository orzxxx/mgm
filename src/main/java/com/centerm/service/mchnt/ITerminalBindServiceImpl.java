package com.centerm.service.mchnt;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.mchnt.TerminalBindInf;

public interface ITerminalBindServiceImpl {
	public List<TerminalBindInf> list(TerminalBindInf terminalBind, Page page) throws Exception;
	
	public int del(TerminalBindInf terminalBind);
	
	public int add(TerminalBindInf terminalBind);
	
	public int update(TerminalBindInf terminalBind);
}