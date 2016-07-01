package com.centerm.service.mchnt;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.mchnt.FrchseAuditInf;

public interface IFrchseAuditServiceImpl {
	public List<FrchseAuditInf> list(FrchseAuditInf frchseAudit, Page page) throws Exception;
	
	public int del(int id);
	
	public void add(FrchseAuditInf frchseAudit);
	
	public void update(FrchseAuditInf frchseAudit);

	public FrchseAuditInf getByMchntCd(String mchntCd);
}