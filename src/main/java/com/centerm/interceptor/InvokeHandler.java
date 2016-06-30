package com.centerm.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.centerm.base.Constant;
import com.centerm.base.ResponseBean;
import com.centerm.exception.BusinessException;
import com.centerm.model.sys.LoginUser;
import com.centerm.utils.StringUtils;
import com.google.gson.Gson;

/**
 * 统一流程数据封装
 */
@Component
@Aspect
public class InvokeHandler{
	
	private static Logger logger = Logger.getLogger(InvokeHandler.class);

	@Pointcut("execution(* com.centerm.controller..*(..))")  
	public void controllerMethod(){}
	
	@Around("controllerMethod()")  
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{  
		ResponseBean resultBean = new ResponseBean();
		//请求日志
		printRequestLog(pjp);
		try {
			 Object retVal = pjp.proceed();
			 resultBean.setData(retVal);
			 resultBean.setCode(Constant.RESULT_SUCCEESS);
		} catch (BusinessException e) {
			//错误日志
			//printExceptionLog(pjp, e);
			//logger.info(e);
			resultBean.setCode(Constant.RESULT_BUSINESS_FAILD);
			resultBean.setMessage(e.getMessage());
		} catch (Exception e){
			//错误日志
			printExceptionLog(pjp, e);
			resultBean.setCode(Constant.RESULT_SYSTEM_FAILD);
			resultBean.setMessage(Constant.ERROR_SYSTEM_MSG);
		}
		//返回日志
		printResponseLog(pjp, resultBean);
		
        return resultBean;  
    } 
	
	private void printRequestLog(ProceedingJoinPoint pjp) throws Exception{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes()).getRequest(); 
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();
		String method = pjp.getTarget().getClass().getSimpleName()+"."+pjp.getSignature().getName();
		Map param = request.getParameterMap();//multipart/form-data类型的无法获取
		String jsonData = new Gson().toJson(param);
		LoginUser loginUser = (LoginUser) session.getAttribute(Constant.LOGIN_USER);
		logger.debug("=========收到请求==========");
		if (loginUser != null) {
			logger.debug("   用户ID:"+loginUser.getUserInfo().getUserId());
		}
		logger.debug("   请求方法:"+method);
		logger.debug("   请求参数:"+jsonData);
	}
	
	private void printResponseLog(ProceedingJoinPoint pjp, ResponseBean respBean){
		Gson gson = new Gson();
		String jsonData = gson.toJson(respBean.getData());
		Object data;
		try {
			data = gson.fromJson(jsonData, Map.class);
		} catch (Exception e) {
			//无法转成Map那就是数组类型,直接输出
			data = jsonData;
		}
		
		String method = pjp.getTarget().getClass().getSimpleName()+"."+pjp.getSignature().getName();
		logger.debug("=========返回结果==========");
		logger.debug("   请求方法:"+method);
		logger.debug("   执行结果:"+respBean.getCode());
		if (!StringUtils.isNull(respBean.getMessage())) {
			logger.debug("   错误信息:"+respBean.getMessage());
		}
		if (respBean.getData() != null){
			logger.debug("   返回数据:"+data);
		}
	}
	
	private void printExceptionLog(ProceedingJoinPoint pjp, Exception e){
		logger.error("=========错误信息==========");
		logger.error(getTrace(e));
	}
	
	public String getTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter writer= new PrintWriter(sw);
		t.printStackTrace(writer);
		StringBuffer buffer= sw.getBuffer();
		return buffer.toString();
	}
}
