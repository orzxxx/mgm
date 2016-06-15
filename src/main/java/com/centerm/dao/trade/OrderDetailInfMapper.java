package com.centerm.dao.trade;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.trade.OrderDetailInf;

public interface OrderDetailInfMapper extends BaseMapper<OrderDetailInf>{
		
	public List<OrderDetailInf> findByOrderNo(String orderNo);
		
}