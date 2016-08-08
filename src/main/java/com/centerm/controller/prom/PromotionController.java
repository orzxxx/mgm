package com.centerm.controller.prom;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.centerm.base.Page;
import com.centerm.exception.BusinessException;
import com.centerm.model.menu.Sreenshot;
import com.centerm.model.prom.PromotionInf;
import com.centerm.service.prom.IPromotionServiceImpl;
import com.centerm.utils.ImageUtils;
import com.centerm.utils.PropertyUtils;
import com.centerm.utils.StringUtils;

@Controller
@RequestMapping("prom/prom")
public class PromotionController {

	private IPromotionServiceImpl promotionServiceImpl;

	public IPromotionServiceImpl getPromotionServiceImpl() {
		return promotionServiceImpl;
	}
	@Autowired
	public void setPromotionServiceImpl(IPromotionServiceImpl promotionServiceImpl) {
		this.promotionServiceImpl = promotionServiceImpl;
	}

	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("promotionInf") PromotionInf promotion, 
			@RequestParam int currentPage, 
			@RequestParam int pageSize) throws Exception {
		Page page = new Page(currentPage, pageSize);
		List<PromotionInf> promotions = promotionServiceImpl.list(promotion, page);
		String serverAddress = PropertyUtils.getProperties("serverAddress")+"/"+promotion.getMchntCd()+"/";
		for (PromotionInf prom : promotions) {
			prom.setPictureLink(serverAddress+prom.getPictureLink());
		}
		page.setRows(promotions);
		return page;
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(@ModelAttribute("promotionInf") PromotionInf promotion) throws Exception {
		promotionServiceImpl.del(promotion);
		return null;
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upload")
	@ResponseBody()
	public Object upload(@ModelAttribute("Sreenshot") Sreenshot ss, HttpServletRequest request) throws Exception {
        //图片保存截取和压缩
        File imageFile = ImageUtils.getImageFile(ss.getMchntCd(), ss, ImageUtils.PROMOTION);
        //保存图片链接
        Map<String, String> data = new HashMap<String, String>();
        data.put("pictureLink", imageFile.getName());
        data.put("pictureAddr", PropertyUtils.getProperties("serverAddress")+"/"+ss.getMchntCd()+"/"+imageFile.getName());
        return data;
	}
	
	/*@RequestMapping("/upload")
	@ResponseBody()
	public Object upload(@ModelAttribute("promotionInf") PromotionInf promotion, HttpServletRequest request) throws Exception {
		//保存图片
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
        MultipartFile picture =  multipartRequest.getFile("picture"); 
        if (ImageUtils.imageFormatValidate(picture)) {
        	//图片保存并压缩处理
        	File imageFile = ImageUtils.getImageFile(promotion.getMchntCd(), picture.getInputStream(), ImageUtils.PROMOTION);
        	//保存图片
        	//picture.transferTo(imageFile);
        	//保存图片链接
        	Map<String, String> data = new HashMap<String, String>();
        	data.put("pictureLink", imageFile.getName());
        	data.put("pictureAddr", PropertyUtils.getProperties("serverAddress")+"/"+promotion.getMchntCd()+"/"+imageFile.getName());
        	return data;
		}else{
			throw new BusinessException("非法的图片格式");
		}
	}*/
	
	@RequestMapping("/add")
	@ResponseBody()
	public Object add(@ModelAttribute("promotionInf") PromotionInf promotion, HttpServletRequest request) throws Exception {
		promotionServiceImpl.add(promotion);
		return null;
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("promotionInf") PromotionInf promotion, HttpServletRequest request) throws Exception {
		if (StringUtils.isNull(promotion.getPictureLink())) {
			promotion.setPictureLink(null);
		}
		promotionServiceImpl.update(promotion);
		return null;
	}
}
