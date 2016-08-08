package com.centerm.service.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.report.SalesInfMapper;
import com.centerm.model.report.SalesInf;
import com.centerm.service.report.ISalesService;
import com.centerm.utils.BeanUtil;

@Service("salesService")
@Transactional
public class SalesServiceImpl implements ISalesService{

	private SalesInfMapper salesMapper;

	public SalesInfMapper getSalesMapper() {
		return salesMapper;
	}
	@Autowired
	public void setSalesMapper(SalesInfMapper salesMapper) {
		this.salesMapper = salesMapper;
	}
	
	public List<SalesInf> list(SalesInf sales, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(sales);
		map.put("page", page);
		return salesMapper.query(map);
	}
	
	public int del(int id){
		return salesMapper.deleteByPrimaryKey(id);
	}
	
	public int add(SalesInf sales){
		return salesMapper.insert(sales);
	}
	
	public int update(SalesInf sales){
		return salesMapper.updateByPrimaryKeySelective(sales);
	}
}
