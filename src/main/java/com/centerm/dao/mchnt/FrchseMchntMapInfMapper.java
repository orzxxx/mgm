package com.centerm.dao.mchnt;

import java.util.List;
import java.util.Map;

import com.centerm.base.BaseMapper;
import com.centerm.model.mchnt.FrchseMchntMapInf;
import com.centerm.model.mchnt.MchntInf;

public interface FrchseMchntMapInfMapper extends BaseMapper<FrchseMchntMapInf>{
	public List<MchntInf> selectMchntByFrchseCd(Map<String, Object> map);
	
	public String selectFrchseCdByMchntCd(String mchntCd);
}