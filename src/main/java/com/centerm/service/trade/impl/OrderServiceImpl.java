package com.centerm.service.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.trade.OrderDetailInfMapper;
import com.centerm.dao.trade.OrderJourInfMapper;
import com.centerm.model.trade.OrderDetailInf;
import com.centerm.model.trade.OrderJourInf;
import com.centerm.service.trade.IOrderServiceImpl;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.StringUtils;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements IOrderServiceImpl{

	private OrderJourInfMapper orderJourMapper;
	
	private OrderDetailInfMapper orderDetailInfMapper;

	public OrderDetailInfMapper getOrderDetailInfMapper() {
		return orderDetailInfMapper;
	}
	@Autowired
	public void setOrderDetailInfMapper(OrderDetailInfMapper orderDetailInfMapper) {
		this.orderDetailInfMapper = orderDetailInfMapper;
	}
	public OrderJourInfMapper getOrderJourMapper() {
		return orderJourMapper;
	}
	@Autowired
	public void setOrderJourMapper(OrderJourInfMapper orderJourMapper) {
		this.orderJourMapper = orderJourMapper;
	}

	public List<OrderJourInf> list(OrderJourInf order, Page page) throws Exception{
		//模糊查询
		if(!StringUtils.isNull(order.getOrderNo())){
			order.setOrderNo("%"+order.getOrderNo()+"%");
		}
		if(!StringUtils.isNull(order.getPseq())){
			order.setPseq("%"+order.getPseq()+"%");
		}
		if(!StringUtils.isNull(order.getMchntCd())){
			order.setMchntCd("%"+order.getMchntCd()+"%");
		}
		Map<String,Object> map = BeanUtil.bean2Map(order);
		map.put("page", page);
		return orderJourMapper.query(map);
	}
	
	public int del(int id){
		return orderJourMapper.deleteByPrimaryKey(id);
	}
	
	public int add(OrderJourInf order){
		return orderJourMapper.insert(order);
	}
	
	public int update(OrderJourInf order){
		return orderJourMapper.updateByPrimaryKeySelective(order);
	}
	@Override
	public List<OrderDetailInf> getDetails(String orderNo) {
		return orderDetailInfMapper.findByOrderNo(orderNo);
	}
}
