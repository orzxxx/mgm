package com.centerm.service.trade;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.centerm.base.Page;
import com.centerm.model.trade.OrderDetailInf;
import com.centerm.model.trade.OrderJourInf;

public interface IOrderService {
	public List<OrderJourInf> list(OrderJourInf order, Page page) throws Exception;
	
	public int del(int id);
	
	public int add(OrderJourInf order);
	
	public int update(OrderJourInf order);

	public List<OrderDetailInf> getDetails(String orderNo);
	
	public HSSFWorkbook export(OrderJourInf order) throws Exception;
}