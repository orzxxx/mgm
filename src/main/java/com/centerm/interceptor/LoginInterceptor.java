package com.centerm.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.centerm.base.Constant;
import com.centerm.model.sys.LoginUser;
import com.google.gson.Gson;
/**
 * 登录拦截器
 * 
 */
public class LoginInterceptor implements HandlerInterceptor  {
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object object, ModelAndView mv) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object object) throws Exception {
		
		String path = request.getContextPath();
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser)session.getAttribute(Constant.LOGIN_USER);
		if(null != loginUser) {
			return true;
		}
		
		String redirectUrl = path + "/error/nologin.jsp";
		if (request.getHeader("x-requested-with") != null) {
			//response.getWriter().print("window.loaction="+path);
			Map<String,Object> msg = new HashMap<String,Object>();
			msg.put("loginStatus", false);
			msg.put("redirectUrl", redirectUrl);
            response.getWriter().print(new Gson().toJson(msg));
        } else {
        	// 否则跳转方式至登录页
        	response.sendRedirect(redirectUrl);
        }
		return false;
	}
}
