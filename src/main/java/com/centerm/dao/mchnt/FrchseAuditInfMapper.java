package com.centerm.dao.mchnt;

import com.centerm.base.BaseMapper;
import com.centerm.model.mchnt.FrchseAuditInf;

public interface FrchseAuditInfMapper extends BaseMapper<FrchseAuditInf>{

	public FrchseAuditInf getByMchntCd(String mchntCd);

	public void deleteByMchntCd(String mchntCd);
}