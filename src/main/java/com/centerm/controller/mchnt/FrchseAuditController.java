package com.centerm.controller.mchnt;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.centerm.base.Page;
import com.centerm.model.mchnt.FrchseAuditInf;
import com.centerm.service.mchnt.IFrchseAuditService;

@Controller
@RequestMapping("mchnt/assoc")
public class FrchseAuditController {

	private IFrchseAuditService frchseAuditServiceImpl;

	public IFrchseAuditService getFrchseAuditServiceImpl() {
		return frchseAuditServiceImpl;
	}
	@Autowired
	public void setFrchseAuditServiceImpl(IFrchseAuditService frchseAuditServiceImpl) {
		this.frchseAuditServiceImpl = frchseAuditServiceImpl;
	}

	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("frchseAuditInf") FrchseAuditInf frchseAudit, 
			@RequestParam int currentPage, 
			@RequestParam int pageSize) throws Exception {
		Page page = new Page(currentPage, pageSize);
		List<FrchseAuditInf> frchseAudits = frchseAuditServiceImpl.list(frchseAudit, page);
		page.setRows(frchseAudits);
		return page;
	}
	
	@RequestMapping("/get")
	@ResponseBody()
	public Object get(String mchntCd){
		return frchseAuditServiceImpl.getByMchntCd(mchntCd);
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(int id) throws Exception {
		frchseAuditServiceImpl.del(id);
		return null;
	}
	
	@RequestMapping("/add")
	@ResponseBody()
	public Object add(@ModelAttribute("frchseAuditInf") FrchseAuditInf frchseAudit) throws Exception {
		frchseAuditServiceImpl.add(frchseAudit);
		return null;
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("frchseAuditInf") FrchseAuditInf frchseAudit) throws Exception {
		frchseAuditServiceImpl.update(frchseAudit);
		return null;
	}
}
