package com.centerm.controller.mchnt;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.centerm.base.Page;
import com.centerm.model.mchnt.TerminalBindInf;
import com.centerm.service.mchnt.ITerminalBindServiceImpl;

@Controller
@RequestMapping("mchnt/terminal")
public class TerminalBindController {

	private ITerminalBindServiceImpl terminalBindServiceImpl;

	public ITerminalBindServiceImpl getTerminalBindServiceImpl() {
		return terminalBindServiceImpl;
	}
	@Autowired
	public void setTerminalBindServiceImpl(ITerminalBindServiceImpl terminalBindServiceImpl) {
		this.terminalBindServiceImpl = terminalBindServiceImpl;
	}

	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("terminalBindInf") TerminalBindInf terminalBind, 
			@RequestParam int currentPage, 
			@RequestParam int pageSize) throws Exception {
		Page page = new Page(currentPage, pageSize);
		List<TerminalBindInf> terminalBinds = terminalBindServiceImpl.list(terminalBind, page);
		page.setRows(terminalBinds);
		return page;
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(TerminalBindInf terminalBind) throws Exception {
		terminalBindServiceImpl.del(terminalBind);
		return null;
	}
	
	@RequestMapping("/add")
	@ResponseBody()
	public Object add(@ModelAttribute("terminalBindInf") TerminalBindInf terminalBind) throws Exception {
		terminalBindServiceImpl.add(terminalBind);
		return null;
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("terminalBindInf") TerminalBindInf terminalBind) throws Exception {
		terminalBindServiceImpl.update(terminalBind);
		return null;
	}
}
