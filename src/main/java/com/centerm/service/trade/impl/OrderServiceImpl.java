package com.centerm.service.trade.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.trade.OrderDetailInfMapper;
import com.centerm.dao.trade.OrderJourInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.trade.OrderDetailInf;
import com.centerm.model.trade.OrderJourInf;
import com.centerm.service.trade.IOrderServiceImpl;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.ExcelUtils;
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
	
	public HSSFWorkbook export(OrderJourInf order) throws Exception {
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
		List<OrderJourInf> result = orderJourMapper.queryForExcel(order);
		if (result == null || result.size() == 0) {
			throw new BusinessException("没有符合条件的数据!");
		}
		//Excel导出
		String title = "Q哥点餐商户订单流水报表";  
	    String[] rowsName = new String[]{"订单号", "平台流水", "商户号", "商户名称", 
	    		"交易日期", "交易时间", "订单金额(元)", "付款类型", "交易类型"};  
	    List<Object[]>  dataList = new ArrayList<Object[]>();  
	    Object[] objs = null;  
	    for (int i = 0; i < result.size(); i++) {  
	    	OrderJourInf orderJour = result.get(i);
        	//数据显示处理
        	String transdate = orderJour.getTransdate();
        	transdate = transdate.substring(0,4)+"/"+transdate.substring(4,6)+"/"+transdate.substring(6,8);
        	String transtime = orderJour.getTranstime();
        	transtime = transtime.substring(0,2)+":"+transtime.substring(2,4)+":"+transtime.substring(4,6);
        	DecimalFormat df = new DecimalFormat("#######0.00");   
        	String stdtrnsamt = df.format(orderJour.getStdtrnsamt().doubleValue());
        	String payTp = "";
        	if (orderJour.getPayTp().equals("0")){
        		payTp = "现金";
        	} else if (orderJour.getPayTp().equals("1")){
        		payTp = "银行卡";
        	} else if (orderJour.getPayTp().equals("2")){
        		payTp = "拉卡拉扫码";
        	} else if (orderJour.getPayTp().equals("3")){
        		payTp = "微信扫码";
        	} else if (orderJour.getPayTp().equals("4")){
        		payTp = "支付宝扫码";
        	} else if (orderJour.getPayTp().equals("5")){
        		payTp = "微信被扫码";
        	} else if (orderJour.getPayTp().equals("6")){
        		payTp = "支付宝被扫码";
        	} 
        	String trnsflag = "";
        	if (orderJour.getTrnsflag().equals(-1)){
        		trnsflag = "交易失败";
        	} else if (orderJour.getTrnsflag().equals(0)){
        		trnsflag = "交易初始化";
        	} else if (orderJour.getTrnsflag().equals(1)){
        		trnsflag = "交易成功";
        	} else if (orderJour.getTrnsflag().equals(2)){
        		trnsflag = "交易撤销";
        	} else if (orderJour.getTrnsflag().equals(3)){
        		trnsflag = "退单成功";
        	}
        	
        	objs = new Object[rowsName.length];  
        	objs[0] = orderJour.getOrderNo();  
		    objs[1] = orderJour.getPseq();
		    objs[2] = orderJour.getMchntCd();
		    objs[3] = orderJour.getMchntName();
		    objs[4] = transdate;  
		    objs[5] = transtime;  
		    objs[6] = stdtrnsamt;  
		    objs[7] = payTp;  
		    objs[8] = trnsflag;  
		    dataList.add(objs);  
	    }  
	    try {
	    	return ExcelUtils.export(title, rowsName, dataList);  
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("导出订单报表失败");
		}
	    
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
