package com.centerm.controller.template;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.centerm.base.Page;
import com.centerm.exception.BusinessException;
import com.centerm.model.menu.ProductAttrTypeInf;
import com.centerm.model.template.MenuTemplateInf;
import com.centerm.service.sys.impl.GetSequenceService;
import com.centerm.service.template.IMenuTemplateService;
import com.centerm.utils.ImageUtils;
import com.centerm.utils.PropertyUtils;
import com.centerm.utils.StringUtils;

@Controller
@RequestMapping("/template/menu")
public class MenuTemplateController {

	private IMenuTemplateService menuTemplateServiceImpl;

	private GetSequenceService getSequenceService;

	public GetSequenceService getGetSequenceService() {
		return getSequenceService;
	}
	@Autowired
	public void setGetSequenceService(GetSequenceService getSequenceService) {
		this.getSequenceService = getSequenceService;
	}
	
	public IMenuTemplateService getMenuTemplateServiceImpl() {
		return menuTemplateServiceImpl;
	}
	@Autowired
	public void setMenuTemplateServiceImpl(IMenuTemplateService menuTemplateServiceImpl) {
		this.menuTemplateServiceImpl = menuTemplateServiceImpl;
	}

	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("menuTemplateInf") MenuTemplateInf menu, 
			@RequestParam int currentPage, 
			@RequestParam int pageSize) throws Exception {
		Page page = new Page(currentPage, pageSize);
		//只查找未删除的
		menu.setStatus(0);
		List<MenuTemplateInf> menus = menuTemplateServiceImpl.list(menu, page);
		String serverAddress = PropertyUtils.getProperties("serverAddress")+"/"+menu.getMchntCd()+"/";
		for (MenuTemplateInf menuInf : menus) {
			menuInf.setPictureLink(serverAddress+menuInf.getPictureLink());
		}
		page.setRows(menus);
		return page;
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(String productId) throws Exception {
		//伪删除
		MenuTemplateInf menu = new MenuTemplateInf();
		menu.setProductId(productId);
		menu.setStatus(-1);
		
		menuTemplateServiceImpl.del(menu);
		return null;
	}
	
	@RequestMapping("/add")
	@ResponseBody()
	public Object add(@ModelAttribute("menuTemplateInf") MenuTemplateInf menu, @RequestParam("productAttrTypeJson")String productAttrTypeJson) throws Exception {
        menu.setProductId(getSequenceService.GetNewProductId(false));
        menu.setStatus(0);
        (menu.getInventory()).setProductId(menu.getProductId());
		menuTemplateServiceImpl.add(menu, JSON.parseArray(productAttrTypeJson, ProductAttrTypeInf.class));
		return null;
	}
	
	@RequestMapping("/upload")
	@ResponseBody()
	public Object upload(@ModelAttribute("menuInf") MenuTemplateInf menu, HttpServletRequest request) throws Exception {
		//保存图片
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
        MultipartFile picture =  multipartRequest.getFile("picture"); 
        if (ImageUtils.imageFormatValidate(picture)) {
        	//图片保存并压缩处理
        	File imageFile = ImageUtils.getImageFile(menu.getMchntCd(), picture.getInputStream(), ImageUtils.MENU);
        	//保存图片
        	//picture.transferTo(imageFile);
        	//保存图片链接
        	Map<String, String> data = new HashMap<String, String>();
        	data.put("pictureLink", imageFile.getName());
        	data.put("pictureAddr", PropertyUtils.getProperties("serverAddress")+"/"+menu.getMchntCd()+"/"+imageFile.getName());
        	return data;
		}else{
			throw new BusinessException("非法的图片格式");
		}
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("menuTemplateInf") MenuTemplateInf menu, @RequestParam("productAttrTypeJson")String productAttrTypeJson) throws Exception {
		if (StringUtils.isNull(menu.getPictureLink())) {
			menu.setPictureLink(null);
		}
		menuTemplateServiceImpl.update(menu, JSON.parseArray(productAttrTypeJson, ProductAttrTypeInf.class));
		return null;
	}
}
