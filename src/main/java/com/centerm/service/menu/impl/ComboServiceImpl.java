package com.centerm.service.menu.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.menu.ComboDetailInfMapper;
import com.centerm.dao.menu.ComboInfMapper;
import com.centerm.dao.menu.InventoryInfMapper;
import com.centerm.dao.menu.MenuTypeInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.menu.ComboDetailInf;
import com.centerm.model.menu.ComboInf;
import com.centerm.model.menu.MenuTypeInf;
import com.centerm.service.menu.IComboServiceImpl;
import com.centerm.service.sys.impl.GetSequenceService;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.StringUtils;

@Service("comboService")
@Transactional
public class ComboServiceImpl implements IComboServiceImpl{

	private ComboInfMapper comboMapper;
	
	private ComboDetailInfMapper comboDetailMapper;
	
	private InventoryInfMapper inventoryMapper;
	
	private MenuTypeInfMapper menuTypeMapper;
	
	private GetSequenceService getSequenceService;

	public GetSequenceService getGetSequenceService() {
		return getSequenceService;
	}
	@Autowired
	public void setGetSequenceService(GetSequenceService getSequenceService) {
		this.getSequenceService = getSequenceService;
	}
	
	public MenuTypeInfMapper getMenuTypeMapper() {
		return menuTypeMapper;
	}
	@Autowired
	public void setMenuTypeMapper(MenuTypeInfMapper menuTypeMapper) {
		this.menuTypeMapper = menuTypeMapper;
	}
	public InventoryInfMapper getInventoryMapper() {
		return inventoryMapper;
	}
	@Autowired
	public void setInventoryMapper(InventoryInfMapper inventoryMapper) {
		this.inventoryMapper = inventoryMapper;
	}

	public ComboDetailInfMapper getComboDetailMapper() {
		return comboDetailMapper;
	}
	@Autowired
	public void setComboDetailMapper(ComboDetailInfMapper comboDetailMapper) {
		this.comboDetailMapper = comboDetailMapper;
	}
	public ComboInfMapper getComboMapper() {
		return comboMapper;
	}
	@Autowired
	public void setComboMapper(ComboInfMapper comboMapper) {
		this.comboMapper = comboMapper;
	}
	
	public List<ComboInf> list(ComboInf combo, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(combo);
		map.put("page", page);
		return comboMapper.query(map);
	}
	
	public int del(ComboInf combo){
		//inventoryMapper.deleteByPrimaryKey(combo.getProductId());
		comboMapper.updateByPrimaryKeySelective(combo);
		return comboDetailMapper.deleteByComboId(combo.getProductId());
	}
	
	public int add(ComboInf combo){
		//设置分类id
		String comboMenuupId = menuTypeMapper.getComboMenutpId(combo.getMchntCd());
		if(StringUtils.isNull(comboMenuupId)){
			//第一次新增为空时添加
			//添加默认套餐分类
			MenuTypeInf menuType = new MenuTypeInf();
			menuType.setPriority(0);
			menuType.setMenutpName("套餐");
			menuType.setStatus(0);
			menuType.setMenutpId(getSequenceService.CreateNewMenutpId(true));
			menuType.setMchntCd(combo.getMchntCd());
			menuTypeMapper.insert(menuType);
			comboMenuupId = menuType.getMenutpId();
		}
		combo.setMenutpId(comboMenuupId);
		//唯一性校验
		int num = comboMapper.isNameExisted(combo);
		if (num > 0) {
			throw new BusinessException("套餐名已存在");
		}
		//设置优先级
		int maxPriority = comboMapper.queryMaxPriority(combo.getMchntCd());
		combo.setPriority(maxPriority+1);
		//详情拼接
		String productDetail = "";
		List<ComboDetailInf> comboDetails = combo.getComboDetails();
		for (ComboDetailInf comboDetail : comboDetails) {
			comboDetail.setComboId(combo.getProductId());
			//套餐详情
			productDetail += comboDetail.getProductName();
			if (!StringUtils.isNull(comboDetail.getSelectedAttr())) {
				productDetail += "("+comboDetail.getSelectedAttr()+")";
			}
			productDetail += " x"+comboDetail.getNum()+"\r\n";
		}
		combo.setProductDetail(productDetail);
		inventoryMapper.insert(combo.getInventory());
		comboDetailMapper.insertbatch(comboDetails);
		return comboMapper.insert(combo);
	}
	
	public int update(ComboInf combo){
		//唯一性校验
		int num = comboMapper.isNameExisted(combo);
		if (num > 0) {
			throw new BusinessException("套餐名已存在");
		}
		//套餐详细重新添加
		comboDetailMapper.deleteByComboId(combo.getProductId());
		//详情拼接
		String productDetail = "";
		List<ComboDetailInf> comboDetails = combo.getComboDetails();
		for (ComboDetailInf comboDetail : comboDetails) {
			comboDetail.setComboId(combo.getProductId());
			//套餐详情
			productDetail += comboDetail.getProductName();
			if (!StringUtils.isNull(comboDetail.getSelectedAttr())) {
				productDetail += "("+comboDetail.getSelectedAttr()+")";
			}
			productDetail += " x"+comboDetail.getNum()+"\r\n";
		}
		combo.setProductDetail(productDetail);
		comboDetailMapper.insertbatch(comboDetails);
		
		inventoryMapper.updateByPrimaryKeySelective(combo.getInventory());
		return comboMapper.updateByPrimaryKeySelective(combo);
	}
	@Override
	public List<ComboDetailInf> getDetails(String comboId) {
		return comboDetailMapper.findByComboId(comboId);
	}
}
