package com.centerm.dao.trade;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.trade.OrderJourInf;

public interface OrderJourInfMapper extends BaseMapper<OrderJourInf>{
	public List<OrderJourInf> queryForExcel(OrderJourInf orderJour);
}