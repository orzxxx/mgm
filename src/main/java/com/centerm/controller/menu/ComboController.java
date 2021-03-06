package com.centerm.controller.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.centerm.base.Page;
import com.centerm.model.menu.ChildComboTypeInf;
import com.centerm.model.menu.ComboInf;
import com.centerm.service.menu.IComboService;
import com.centerm.service.sys.impl.GetSequenceService;
import com.centerm.utils.PropertyUtils;
import com.centerm.utils.StringUtils;

@Controller
@RequestMapping("/menu/combo")
public class ComboController {

	private IComboService comboServiceImpl;
	
	private GetSequenceService getSequenceService;

	public GetSequenceService getGetSequenceService() {
		return getSequenceService;
	}
	@Autowired
	public void setGetSequenceService(GetSequenceService getSequenceService) {
		this.getSequenceService = getSequenceService;
	}

	public IComboService getComboServiceImpl() {
		return comboServiceImpl;
	}
	@Autowired
	public void setComboServiceImpl(IComboService comboServiceImpl) {
		this.comboServiceImpl = comboServiceImpl;
	}

	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("comboInf") ComboInf combo, 
			@RequestParam int currentPage, 
			@RequestParam int pageSize, 
			HttpSession session) throws Exception {
		Page page = new Page(currentPage, pageSize);
		if (combo.getStatus() == null) {
			combo.setStatus(9);//0+1
		}
		List<ComboInf> combos = comboServiceImpl.list(combo, page);
		String serverAddress = PropertyUtils.getProperties("serverAddress")+"/"+combo.getMchntCd()+"/";
		for (ComboInf comboInf : combos) {
			comboInf.setPictureLink(serverAddress+comboInf.getPictureLink());
		}
		page.setRows(combos);
		return page;
	}
	
	@RequestMapping("/details")
	@ResponseBody()
	public Object getChildren(String comboId) throws Exception {
		return comboServiceImpl.geChildren(comboId);
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(@ModelAttribute("comboInf") ComboInf combo) throws Exception {
		//ComboInf combo = new ComboInf();
		//combo.setProductId(productId);
		combo.setStatus(-1);
		comboServiceImpl.del(combo);
		return null;
	}
	
	/*@RequestMapping("/upload")
	@ResponseBody()
	public Object upload(@ModelAttribute("comboInf") ComboInf combo, HttpServletRequest request) throws Exception {
		//保存图片
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
        MultipartFile picture =  multipartRequest.getFile("picture"); 
        if (ImageUtils.imageFormatValidate(picture)) {
        	//图片保存并压缩处理
        	File imageFile = ImageUtils.getImageFile(combo.getMchntCd(), picture.getInputStream(), ImageUtils.MENU);
        	//保存图片
        	//picture.transferTo(imageFile);
        	//保存图片链接
        	Map<String, String> data = new HashMap<String, String>();
        	data.put("pictureLink", imageFile.getName());
        	data.put("pictureAddr", PropertyUtils.getProperties("serverAddress")+"/"+combo.getMchntCd()+"/"+imageFile.getName());
        	return data;
		}else{
			throw new BusinessException("非法的图片格式");
		}
	}*/
	
	@RequestMapping("/add")
	@ResponseBody()
	public Object add(@ModelAttribute("comboInf") ComboInf combo, @RequestParam("childComboTypeJson")String childComboTypeJson, HttpServletRequest request) throws Exception {
		combo.setProductId(getSequenceService.GetNewProductId(true));
		combo.setStatus(0);
       (combo.getInventory()).setProductId(combo.getProductId());
		comboServiceImpl.add(combo, JSON.parseArray(childComboTypeJson, ChildComboTypeInf.class));
		return null;
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("comboInf") ComboInf combo, @RequestParam("childComboTypeJson")String childComboTypeJson, HttpServletRequest request) throws Exception {
		if (StringUtils.isNull(combo.getPictureLink())) {
			combo.setPictureLink(null);
		}
		comboServiceImpl.update(combo, JSON.parseArray(childComboTypeJson, ChildComboTypeInf.class));
		return null;
	}
}
