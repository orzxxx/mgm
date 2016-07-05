package com.centerm.controller.menu;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centerm.base.Page;
import com.centerm.model.menu.MenuTypeInf;
import com.centerm.service.menu.IMenuTypeServiceImpl;
import com.centerm.service.sys.impl.GetSequenceService;

@Controller
@RequestMapping("/menu/type")
public class MenuTypeController {

	private IMenuTypeServiceImpl menuTypeServiceImpl;

	private GetSequenceService getSequenceService;

	public GetSequenceService getGetSequenceService() {
		return getSequenceService;
	}
	@Autowired
	public void setGetSequenceService(GetSequenceService getSequenceService) {
		this.getSequenceService = getSequenceService;
	}
	
	public IMenuTypeServiceImpl getMenuTypeServiceImpl() {
		return menuTypeServiceImpl;
	}
	@Autowired
	public void setMenuTypeServiceImpl(IMenuTypeServiceImpl menuTypeServiceImpl) {
		this.menuTypeServiceImpl = menuTypeServiceImpl;
	}

	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("menuTypeInf")MenuTypeInf menuType,
			@RequestParam int currentPage, 
			@RequestParam int pageSize) throws Exception {
		Page page = new Page(currentPage, pageSize);
		//只查找未删除的
		menuType.setStatus(0);
		List<MenuTypeInf> menutypes = menuTypeServiceImpl.list(menuType, page);
		page.setRows(menutypes);
		return page;
	}
	
	@RequestMapping("/tree")
	@ResponseBody()
	public Object tree(String mchntCd, boolean needCombo) throws Exception {
		List<MenuTypeInf> menuTypes = menuTypeServiceImpl.tree(mchntCd, needCombo);
		return menuTypes;
	}
	
	@RequestMapping("/addTree")
	@ResponseBody()
	public Object addTree(@RequestBody MenuTypeInf[] menuTypes) throws Exception {
		//批量添加分类和单品信息
		List<MenuTypeInf> param = Arrays.asList(menuTypes);
		menuTypeServiceImpl.addTree(param);
		return null;
	}
	
	@RequestMapping("/all")
	@ResponseBody()
	public Object getAll(@ModelAttribute("menuTypeInf")MenuTypeInf menuType) throws Exception {
		//只查找未删除的
		menuType.setStatus(0);
		List<MenuTypeInf> menuTypes = menuTypeServiceImpl.list(menuType, null);
		return menuTypes;
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(String menutpId) throws Exception {
		//伪删除
		MenuTypeInf menuType = new MenuTypeInf();
		menuType.setMenutpId(menutpId);
		menuType.setStatus(-1);
		
		menuTypeServiceImpl.update(menuType);
		return null;
	}
	
	@RequestMapping(value="/add")
	@ResponseBody()
	public Object add(@ModelAttribute("menuTypeInf") MenuTypeInf menuType) throws Exception {
		menuType.setMenutpId(getSequenceService.CreateNewMenutpId(false));
		menuType.setStatus(0);
		menuTypeServiceImpl.add(menuType);
		return null;
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("menuTypeInf") MenuTypeInf menuType) throws Exception {
		menuTypeServiceImpl.update(menuType);
		return null;
	}
}
