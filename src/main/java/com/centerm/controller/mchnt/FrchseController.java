package com.centerm.controller.mchnt;




import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centerm.base.Constant;
import com.centerm.model.mchnt.FrchseInf;
import com.centerm.model.sys.LoginUser;
import com.centerm.service.mchnt.IFrchseService;

@Controller
@RequestMapping("mchnt/frchse")
public class FrchseController {

	private IFrchseService frchseServiceImpl;

	public IFrchseService getFrchseServiceImpl() {
		return frchseServiceImpl;
	}
	@Autowired
	public void setFrchseServiceImpl(IFrchseService frchseServiceImpl) {
		this.frchseServiceImpl = frchseServiceImpl;
	}

	@RequestMapping("/get")
	@ResponseBody()
	public Object get(String frchseCd){
		return frchseServiceImpl.get(frchseCd);
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("frchseInf") FrchseInf frchse, HttpSession session) throws Exception {
		frchseServiceImpl.update(frchse);
        LoginUser loginUser = (LoginUser) session.getAttribute(Constant.LOGIN_USER);
        loginUser.getUserInfo().setUserName(frchse.getUserName());
        session.setAttribute(Constant.LOGIN_USER, loginUser);
		return null;
	}
}
