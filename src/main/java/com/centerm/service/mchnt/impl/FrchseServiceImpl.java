package com.centerm.service.mchnt.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.mchnt.FrchseInfMapper;
import com.centerm.dao.mchnt.FrchseMchntMapInfMapper;
import com.centerm.model.mchnt.FrchseInf;
import com.centerm.model.mchnt.MchntInf;
import com.centerm.service.mchnt.IFrchseServiceImpl;
import com.centerm.utils.BeanUtil;

@Service("frchseService")
@Transactional
public class FrchseServiceImpl implements IFrchseServiceImpl{

	private FrchseInfMapper frchseMapper;
	
	public FrchseInfMapper getFrchseMapper() {
		return frchseMapper;
	}
	@Autowired
	public void setFrchseMapper(FrchseInfMapper frchseMapper) {
		this.frchseMapper = frchseMapper;
	}
	
	public List<FrchseInf> list(FrchseInf frchse, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(frchse);
		map.put("page", page);
		return frchseMapper.query(map);
	}
	public FrchseInf get(String mchntCd){
		return frchseMapper.selectByPrimaryKey(mchntCd);
	}
	public int del(int id){
		return frchseMapper.deleteByPrimaryKey(id);
	}
	
	public int add(FrchseInf frchse){
		return frchseMapper.insert(frchse);
	}
	
	public int update(FrchseInf frchse){
		return frchseMapper.updateByPrimaryKeySelective(frchse);
	}
}
