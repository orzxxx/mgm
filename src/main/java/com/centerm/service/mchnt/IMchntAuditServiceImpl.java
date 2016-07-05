package com.centerm.service.mchnt;

import java.util.List;

import com.centerm.base.Page;
import com.centerm.model.mchnt.MchntAuditInf;

public interface IMchntAuditServiceImpl {
	public List<MchntAuditInf> list(MchntAuditInf mchntAudit, Page page) throws Exception;
	
	public int del(int id);
	
	public int add(MchntAuditInf mchntAudit);
	
	public int update(MchntAuditInf mchntAudit);

	public MchntAuditInf get(String mchntCd);

	public void audit(MchntAuditInf mchntAudit);
}
