package com.centerm.service.sys.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.sys.SequenceInfMapper;



/**
 * 序列处理类
 * 针对mysql没有sequence进行处理
 * 
 * @author CZQ
 * 
 **/
@Service(value="getSequenceService")
public class GetSequenceService {
	Logger logger = Logger.getLogger(this.getClass());
	public SequenceInfMapper sequenceInfMapper;

	
	/**
	 * 生成新的商户ID
	 * 
	 *    
	 * @return 返回ID
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String CreateNewMchntCd() {
		int iMchntCd = 0;
		String strMchntCd = "";
		
		try {
			iMchntCd = sequenceInfMapper.getMchntCd();
			strMchntCd = String.format("%08d", iMchntCd);	

		} catch (DataAccessException e) {
			logger.error("生成新菜单ID失败");
			throw e;
		}
		
		return "600" + strMchntCd;		
	}
	
	/**
	 * 生成新的菜单类型ID
	 * 
	 * @param isCombo
	 *    套餐的菜单类型ID以C开头
	 *    单品的菜单类型ID以S开头
	 *    
	 * @return 返回ID
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String CreateNewMenutpId(boolean isCombo) {
		int iMenutpId = 0;
		String strMenutpId = "";
		
		try {
			iMenutpId = sequenceInfMapper.getMenutpId();
			strMenutpId= String.format("%08d", iMenutpId);	

		} catch (DataAccessException e) {
			logger.error("生成新菜单ID失败");
			throw e;
		}
		
		if(isCombo == true)
		{
			strMenutpId = "C" + strMenutpId;
		}
		else
		{
			strMenutpId = "S" + strMenutpId;
		}
		
		logger.info("生成菜单类型ID：" + strMenutpId);
		
		return strMenutpId;		
	}

	
	/**
	 * 生成新的商品ID
	 * 
	 * @param isCombo
	 *    套餐的商品ID以C开头
	 *    单品的商品ID以S开头
	 *    
	 * @return 返回ID
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String GetNewProductId(boolean isCombo) {
		int iProductId = 0;
		String strProductId = "";
		
		try {
			iProductId = sequenceInfMapper.getProductId();
			strProductId= String.format("%08d", iProductId);	
		} catch (DataAccessException e) {
			logger.error("生成新商品ID失败");
			throw e;
		}
		
		if(isCombo == true)
		{
			strProductId = "C" + strProductId;
		}
		else
		{
			strProductId = "S" + strProductId;
		}
		
		logger.info("生成商品ID：" + strProductId);
		
		return strProductId;					
	}


	public SequenceInfMapper getSequenceInfMapper() {
		return sequenceInfMapper;
	}

	@Autowired
	public void setSequenceInfMapper(SequenceInfMapper sequenceInfMapper) {
		this.sequenceInfMapper = sequenceInfMapper;
	}

}
