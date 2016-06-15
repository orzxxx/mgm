package com.centerm.service.sys;

import com.centerm.model.mchnt.MchntInf;
import com.centerm.model.sys.LoginUser;

public interface ILoginService {
	public LoginUser login(String userCd,String userPwd) throws Exception;

	public LoginUser register(MchntInf mchnt, String passwd) throws Exception;
}
