package com.centerm.controller.mchnt;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.centerm.base.Constant;
import com.centerm.base.Page;
import com.centerm.exception.BusinessException;
import com.centerm.model.mchnt.FrchseInf;
import com.centerm.model.mchnt.MchntInf;
import com.centerm.model.mchnt.OperatorInf;
import com.centerm.model.menu.MenuInf;
import com.centerm.model.sys.LoginUser;
import com.centerm.service.mchnt.IMerchantService;
import com.centerm.utils.ImageUtils;
import com.centerm.utils.PropertyUtils;
import com.centerm.utils.StringUtils;

@Controller
@RequestMapping("/mchnt/mchnt")
public class MerchantController {

	private IMerchantService merchantService;

	public IMerchantService getMerchantService() {
		return merchantService;
	}
	@Autowired
	public void setMerchantService(IMerchantService merchantService) {
		this.merchantService = merchantService;
	}


	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("frchseCd") String frchseCd, 
			@ModelAttribute("mchntName") String mchntName, 
			@RequestParam int currentPage, 
			@RequestParam int pageSize, 
			HttpSession session) throws Exception {
		Page page = new Page(currentPage, pageSize);
		List<MchntInf> mchnts = merchantService.selectMchntByFrchseCd(frchseCd, mchntName, page);
		page.setRows(mchnts);
		return page;
	}
	
	@RequestMapping("/all")
	@ResponseBody()
	public Object list(@ModelAttribute("mchntInf") MchntInf mchnt, 
			@RequestParam int currentPage, 
			@RequestParam int pageSize, 
			HttpSession session) throws Exception {
		Page page = new Page(currentPage, pageSize);
		List<MchntInf> mchnts = merchantService.list(mchnt, page);
		page.setRows(mchnts);
		return page;
	}
	
	@RequestMapping("/get")
	@ResponseBody()
	public Object get(String mchntCd){
		MchntInf mchnt = merchantService.get(mchntCd);
		if (!StringUtils.isNull(mchnt.getMchntLogo())) {
			mchnt.setMchntLogo(PropertyUtils.getProperties("logoServerAddress")+"/"+mchnt.getMchntCd()+"/"+mchnt.getMchntLogo());
		}
		return mchnt;
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("mchntInf") MchntInf mchnt, HttpSession session) throws Exception {
		//地址拼接
		String district = mchnt.getDistrict().replace("/", "");
		String detailedAddress = mchnt.getDetailedAddress();
		mchnt.setMchntAddr(district+detailedAddress);
		merchantService.update(mchnt);
        LoginUser loginUser = (LoginUser) session.getAttribute(Constant.LOGIN_USER);
        loginUser.getUserInfo().setUserName(mchnt.getUserName());
        session.setAttribute(Constant.LOGIN_USER, loginUser);
		return null;
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(int id) throws Exception {
		merchantService.del(id);
		return null;
	}
	
	@RequestMapping("/add")
	@ResponseBody()
	public Object add(@ModelAttribute("mchntInf") MchntInf mchnt, @RequestParam("frchseCd")String frchseCd, @RequestParam("passwd")String passwd) throws Exception {
		String district = mchnt.getDistrict().replace("/", "");
		String detailedAddress = mchnt.getDetailedAddress();
		mchnt.setMchntAddr(district+detailedAddress);
		mchnt.setMobile(mchnt.getUserId());
		mchnt.setStatus(0);
		merchantService.add(mchnt, frchseCd, passwd);
		return null;
	}
	@RequestMapping("/resetPwd")
	@ResponseBody()
	public Object resetPwd(@ModelAttribute("mchntInf") MchntInf mchnt, 
			@RequestParam("oldPwd")String oldPwd, 
			@RequestParam("newPwd")String newPwd) throws Exception {
		merchantService.resetPwd(mchnt, oldPwd, newPwd);
		return null;
	}
	
	@RequestMapping("/logo")
	@ResponseBody()
	public Object upload(@ModelAttribute("mchntInf") MchntInf mchnt, HttpServletRequest request) throws Exception {
		//保存图片
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
        MultipartFile picture =  multipartRequest.getFile("picture"); 
        if (ImageUtils.imageFormatValidate(picture)) {
        	//图片保存并压缩处理
        	File imageFile = ImageUtils.getImageFile(mchnt.getMchntCd(), picture.getInputStream(), ImageUtils.LOGO);
        	//保存图片
        	//picture.transferTo(imageFile);
        	//保存图片链接
        	mchnt.setMchntLogo(imageFile.getName());
        	merchantService.update(mchnt);
        	Map<String, String> data = new HashMap<String, String>();
        	data.put("pictureLink", imageFile.getName());
        	data.put("pictureAddr", PropertyUtils.getProperties("logoServerAddress")+"/"+mchnt.getMchntCd()+"/"+imageFile.getName());
        	return data;
		}else{
			throw new BusinessException("非法的图片格式");
		}
	}
}
