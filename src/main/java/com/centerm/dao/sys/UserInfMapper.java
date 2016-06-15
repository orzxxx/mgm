package com.centerm.dao.sys;

import com.centerm.base.BaseMapper;
import com.centerm.model.sys.UserInf;

public interface UserInfMapper extends BaseMapper<UserInfMapper>{
		public int isNameExisted(UserInf user);

		public UserInf findByMchntCd(String mchntCd);
}