package com.centerm.controller.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centerm.base.Constant;
import com.centerm.exception.BusinessException;
import com.centerm.model.mchnt.MchntInf;
import com.centerm.model.menu.MenuInf;
import com.centerm.model.sys.LoginUser;
import com.centerm.service.sys.ILoginService;

@Controller
public class LoginController {

	private ILoginService loginService;

	public ILoginService getLoginService() {
		return loginService;
	}

	@Autowired
	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	@RequestMapping({"/login"})
	@ResponseBody
	public Object login(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		String userId = request.getParameter("userId");
		String passwd = request.getParameter("passwd");
		if ((userId == null) || ("".equals(userId))) {
			throw new BusinessException("请输入用户名");
		}
		if ((passwd == null) || ("".equals(passwd))) {
			throw new BusinessException("请输入密码");
		}
		//登录并获取登录用户信息
		LoginUser loginUser = this.loginService.login(userId, passwd);
		session.setAttribute(Constant.LOGIN_USER, loginUser);
		
		return null;
	}

	@RequestMapping({"/logout"})
	@ResponseBody
	public Object logout(HttpSession session) {
		session.removeAttribute(Constant.LOGIN_USER);
		return null;
	}
	
	@RequestMapping({"/register"})
	@ResponseBody
	public Object register(@ModelAttribute("mchntInf") MchntInf mchnt, @RequestParam("passwd")String passwd, HttpSession session) throws Exception {
		//地址拼接
		String district = mchnt.getDistrict().replace("/", "");
		String detailedAddress = mchnt.getDetailedAddress();
		mchnt.setMchntAddr(district+detailedAddress);
		mchnt.setMobile(mchnt.getUserId());
		LoginUser loginUser = loginService.register(mchnt, passwd);
		session.setAttribute(Constant.LOGIN_USER, loginUser);
		
		return null;
	}
}