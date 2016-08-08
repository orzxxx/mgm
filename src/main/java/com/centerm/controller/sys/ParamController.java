package com.centerm.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.centerm.base.Page;
import com.centerm.model.sys.ParamInf;
import com.centerm.service.sys.IParamService;

@Controller
@RequestMapping("sys/param")
public class ParamController {

	private IParamService paramServiceImpl;

	public IParamService getParamServiceImpl() {
		return paramServiceImpl;
	}
	@Autowired
	public void setParamServiceImpl(IParamService paramServiceImpl) {
		this.paramServiceImpl = paramServiceImpl;
	}

	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("paramInf") ParamInf param, 
			@RequestParam int currentPage, 
			@RequestParam int pageSize) throws Exception {
		Page page = new Page(currentPage, pageSize);
		List<ParamInf> params = paramServiceImpl.list(param, page);
		page.setRows(params);
		return page;
	}
	
	@RequestMapping("/get")
	@ResponseBody()
	public Object getDate(@ModelAttribute("paramInf") ParamInf param) throws Exception {
		return paramServiceImpl.get(param);
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(int id) throws Exception {
		paramServiceImpl.del(id);
		return null;
	}
	
	@RequestMapping("/add")
	@ResponseBody()
	public Object add(@ModelAttribute("paramInf") ParamInf param) throws Exception {
		paramServiceImpl.add(param);
		return null;
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("paramInf") ParamInf param) throws Exception {
		paramServiceImpl.update(param);
		return null;
	}
}
