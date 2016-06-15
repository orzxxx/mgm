package com.centerm.service.mchnt;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.mchnt.FrchseInf;
import com.centerm.model.mchnt.MchntInf;

public interface IMerchantService {

	public abstract List<MchntInf> list(MchntInf mchnt, Page page)
			throws Exception;

	public abstract MchntInf get(String mchntCd);

	public abstract int update(MchntInf mchnt);
	
	public int del(int id);
	
	public int add(MchntInf mchnt, String frchseCd, String passwd);

	public List<MchntInf> selectMchntByFrchseCd(String frchseCd, String mchntName, Page page) throws Exception;

	public void resetPwd(MchntInf mchnt, String oldPwd, String newPwd);
	
}