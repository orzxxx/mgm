package com.centerm.service.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.report.TurnoverInfMapper;
import com.centerm.model.report.TurnoverInf;
import com.centerm.service.report.ITurnoverServiceImpl;
import com.centerm.utils.BeanUtil;

@Service("turnoverService")
@Transactional
public class TurnoverServiceImpl implements ITurnoverServiceImpl{

	private TurnoverInfMapper turnoverMapper;

	public TurnoverInfMapper getTurnoverMapper() {
		return turnoverMapper;
	}
	@Autowired
	public void setTurnoverMapper(TurnoverInfMapper turnoverMapper) {
		this.turnoverMapper = turnoverMapper;
	}
	
	public List<TurnoverInf> list(TurnoverInf turnover, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(turnover);
		map.put("page", page);
		return turnoverMapper.query(map);
	}
	
	public int del(int id){
		return turnoverMapper.deleteByPrimaryKey(id);
	}
	
	public int add(TurnoverInf turnover){
		return turnoverMapper.insert(turnover);
	}
	
	public int update(TurnoverInf turnover){
		return turnoverMapper.updateByPrimaryKeySelective(turnover);
	}
}
