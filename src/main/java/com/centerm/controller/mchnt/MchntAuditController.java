package com.centerm.controller.mchnt;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
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
import com.centerm.model.mchnt.MchntAuditInf;
import com.centerm.model.menu.ComboInf;
import com.centerm.model.sys.LoginUser;
import com.centerm.model.sys.RoleInf;
import com.centerm.service.mchnt.IMchntAuditServiceImpl;
import com.centerm.utils.DateUtils;
import com.centerm.utils.ImageUtils;
import com.centerm.utils.PropertyUtils;
import com.centerm.utils.StringUtils;

@Controller
@RequestMapping("/mchnt/audit")
public class MchntAuditController {

	private IMchntAuditServiceImpl mchntAuditServiceImpl;

	public IMchntAuditServiceImpl getMchntAuditServiceImpl() {
		return mchntAuditServiceImpl;
	}
	@Autowired
	public void setMchntAuditServiceImpl(IMchntAuditServiceImpl mchntAuditServiceImpl) {
		this.mchntAuditServiceImpl = mchntAuditServiceImpl;
	}

	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("mchntAuditInf") MchntAuditInf mchntAudit, 
			@RequestParam int currentPage, 
			@RequestParam int pageSize, 
			HttpSession session) throws Exception {
		Page page = new Page(currentPage, pageSize);
		//只查找审核中的
		mchntAudit.setAuditStatus(2);
		List<MchntAuditInf> mchntAudits = mchntAuditServiceImpl.list(mchntAudit, page);
		for (MchntAuditInf mchntAuditInf : mchntAudits) {
			String serverAddress = PropertyUtils.getProperties("licenseServerAddress")+"/"+mchntAuditInf.getMchntCd()+"/";
			mchntAuditInf.setIdCardFront(serverAddress+mchntAuditInf.getIdCardFront());
			mchntAuditInf.setIdCardBack(serverAddress+mchntAuditInf.getIdCardBack());
			mchntAuditInf.setLicenseFront(serverAddress+mchntAuditInf.getLicenseFront());
			mchntAuditInf.setStorePhoto(serverAddress+mchntAuditInf.getStorePhoto());
		}
		page.setRows(mchntAudits);
		return page;
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(int id) throws Exception {
		mchntAuditServiceImpl.del(id);
		return null;
	}
	
	@RequestMapping("/add")
	@ResponseBody()
	public Object add(@ModelAttribute("mchntAuditInf") MchntAuditInf mchntAudit) throws Exception {
		mchntAuditServiceImpl.add(mchntAudit);
		return null;
	}
	
	@RequestMapping("/audit")
	@ResponseBody()
	public Object audit(@ModelAttribute("mchntAuditInf") MchntAuditInf mchntAudit) throws Exception {
		mchntAudit.setAuditRole("管理员");
		mchntAudit.setAuditTime(DateUtils.getCurrentDate());
		mchntAuditServiceImpl.audit(mchntAudit);
		return null;
	}
	
	@RequestMapping("/submit")
	@ResponseBody()
	public Object submit(@ModelAttribute("mchntAuditInf") MchntAuditInf mchntAudit) throws Exception {
		mchntAudit = mchntAuditServiceImpl.get(mchntAudit.getMchntCd());
		if (mchntAudit.getAuditStatus() == 2) {
			throw new BusinessException("账号正在审核");
		}
		if (StringUtils.isNull(mchntAudit.getIdCardFront())) {
			throw new BusinessException("未提交身份证正面照");
		}
		if (StringUtils.isNull(mchntAudit.getIdCardBack())) {
			throw new BusinessException("未提交身份证背面照");
		}
		if (StringUtils.isNull(mchntAudit.getLicenseFront())) {
			throw new BusinessException("未提交营业执照");
		}
		if (StringUtils.isNull(mchntAudit.getStorePhoto())) {
			throw new BusinessException("未提交店铺照片");
		}
		mchntAudit.setAuditStatus(2);
		mchntAudit.setSubmitTime(DateUtils.getCurrentDate("yyyyMMddHHmmss"));
		mchntAuditServiceImpl.update(mchntAudit);
		return null;
	}
	
	@RequestMapping("/get")
	@ResponseBody()
	public Object get(String mchntCd){
		MchntAuditInf mchnt = mchntAuditServiceImpl.get(mchntCd);
		String serverAddress = PropertyUtils.getProperties("licenseServerAddress")+"/"+mchnt.getMchntCd()+"/";
		if (!StringUtils.isNull(mchnt.getIdCardFront())) {
			mchnt.setIdCardFront(serverAddress+mchnt.getIdCardFront());
		}
		if (!StringUtils.isNull(mchnt.getIdCardBack())) {
			mchnt.setIdCardBack(serverAddress+mchnt.getIdCardBack());
		}
		if (!StringUtils.isNull(mchnt.getLicenseFront())) {
			mchnt.setLicenseFront(serverAddress+mchnt.getLicenseFront());
		}
		if (!StringUtils.isNull(mchnt.getStorePhoto())) {
			mchnt.setStorePhoto(serverAddress+mchnt.getStorePhoto());
		}
		
		return mchnt;
	}
	
	@RequestMapping("/upload")
	@ResponseBody()
	public Object upload(String mchntCd, String propertyName, HttpServletRequest request) throws Exception {
		//保存图片
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
        MultipartFile picture =  multipartRequest.getFile(propertyName); 
        if (ImageUtils.imageFormatValidate(picture)) {
        	//获取图片路径
        	String imagePath = PropertyUtils.getProperties("licenseSavePath")+"/"+mchntCd+"/";
        	File dir = new File(imagePath);
			if (!dir.exists()) {
				dir.mkdir();
			}
        	String imageName = ImageUtils.getFileName(mchntCd);
        	File imageFile = new File(imagePath+imageName);
        	//保存图片
        	picture.transferTo(imageFile);
        	//保存图片链接
        	PropertyDescriptor pd=new PropertyDescriptor(propertyName, MchntAuditInf.class);
        	Method  method = pd.getWriteMethod();
        	MchntAuditInf mchntAudit = new MchntAuditInf();
        	mchntAudit.setMchntCd(mchntCd);
        	method.invoke(mchntAudit, imageFile.getName());
        	mchntAuditServiceImpl.update(mchntAudit);
        	//回显
        	Map<String, String> data = new HashMap<String, String>();
        	data.put("pictureLink", imageFile.getName());
        	data.put("pictureAddr", PropertyUtils.getProperties("licenseServerAddress")+"/"+mchntAudit.getMchntCd()+"/"+imageFile.getName());
        	return data;
		}else{
			throw new BusinessException("非法的图片格式");
		}
	}
}
