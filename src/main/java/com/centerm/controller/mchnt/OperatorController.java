package com.centerm.controller.mchnt;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.centerm.base.Page;
import com.centerm.model.mchnt.OperatorInf;
import com.centerm.service.mchnt.IOperatorService;
import com.centerm.utils.MD5;

@Controller
@RequestMapping("/mchnt/oper")
public class OperatorController {

	private IOperatorService operatorServiceImpl;

	public IOperatorService getOperatorServiceImpl() {
		return operatorServiceImpl;
	}
	@Autowired
	public void setOperatorServiceImpl(IOperatorService operatorServiceImpl) {
		this.operatorServiceImpl = operatorServiceImpl;
	}

	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("operatorInf") OperatorInf operator, 
			@RequestParam int currentPage, 
			@RequestParam int pageSize) throws Exception {
		Page page = new Page(currentPage, pageSize);
		List<OperatorInf> operators = operatorServiceImpl.list(operator, page);
		page.setRows(operators);
		return page;
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(@ModelAttribute("operatorInf") OperatorInf operator) throws Exception {
		operatorServiceImpl.del(operator);
		return null;
	}
	
	@RequestMapping("/add")
	@ResponseBody()
	public Object add(@ModelAttribute("operatorInf") OperatorInf operator) throws Exception {
		operator.setPasswd(new MD5().getMD5Str("111111"));
		operator.setStatus(0);
		operatorServiceImpl.add(operator);
		return null;
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("operatorInf") OperatorInf operator) throws Exception {
		operatorServiceImpl.update(operator);
		return null;
	}
	
	@RequestMapping("/resetPwd")
	@ResponseBody()
	public Object resetPwd(@ModelAttribute("operatorInf") OperatorInf operator, 
			@RequestParam("newPwd")String newPwd) throws Exception {
		operatorServiceImpl.resetPwd(operator, newPwd);
		return null;
	}
}
