package com.centerm.controller.template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centerm.base.Page;
import com.centerm.model.template.MenuTypeTemplateInf;
import com.centerm.service.sys.impl.GetSequenceService;
import com.centerm.service.template.IMenuTypeTemplateService;

@Controller
@RequestMapping("/template/type")
public class MenuTypeTemplateController {

	private IMenuTypeTemplateService menuTypeTemplateServiceImpl;
	
	private GetSequenceService getSequenceService;

	public GetSequenceService getGetSequenceService() {
		return getSequenceService;
	}
	@Autowired
	public void setGetSequenceService(GetSequenceService getSequenceService) {
		this.getSequenceService = getSequenceService;
	}

	public IMenuTypeTemplateService getMenuTypeTemplateServiceImpl() {
		return menuTypeTemplateServiceImpl;
	}
	@Autowired
	public void setMenuTypeTemplateServiceImpl(IMenuTypeTemplateService menuTypeTemplateServiceImpl) {
		this.menuTypeTemplateServiceImpl = menuTypeTemplateServiceImpl;
	}

	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("menuTypeTemplateInf")MenuTypeTemplateInf menuType,
			@RequestParam int currentPage, 
			@RequestParam int pageSize) throws Exception {
		Page page = new Page(currentPage, pageSize);
		//只查找未删除的
		menuType.setStatus(0);
		List<MenuTypeTemplateInf> menutypes = menuTypeTemplateServiceImpl.list(menuType, page);
		page.setRows(menutypes);
		return page;
	}
	
	@RequestMapping("/tree")
	@ResponseBody()
	public Object tree(String mchntCd) throws Exception {
		List<MenuTypeTemplateInf> menuTypes = menuTypeTemplateServiceImpl.tree(mchntCd);
		return menuTypes;
	}
	
	@RequestMapping("/all")
	@ResponseBody()
	public Object getAll(@ModelAttribute("menuTypeTemplateInf")MenuTypeTemplateInf menuType) throws Exception {
		//只查找未删除的
		menuType.setStatus(0);
		List<MenuTypeTemplateInf> menuTypes = menuTypeTemplateServiceImpl.list(menuType, null);
		return menuTypes;
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(String menutpId) throws Exception {
		//伪删除
		MenuTypeTemplateInf menuType = new MenuTypeTemplateInf();
		menuType.setMenutpId(menutpId);
		menuType.setStatus(-1);
		
		menuTypeTemplateServiceImpl.update(menuType);
		return null;
	}
	
	@RequestMapping("/add")
	@ResponseBody()
	public Object add(@ModelAttribute("menuTypeTemplateInf") MenuTypeTemplateInf menuType) throws Exception {
		menuType.setMenutpId(getSequenceService.CreateNewMenutpId(false));
		menuType.setStatus(0);
		menuTypeTemplateServiceImpl.add(menuType);
		return null;
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("menuTypeTemplateInf") MenuTypeTemplateInf menuType) throws Exception {
		menuTypeTemplateServiceImpl.update(menuType);
		return null;
	}
}
