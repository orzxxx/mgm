package com.centerm.service.menu.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.menu.InventoryInfMapper;
import com.centerm.dao.menu.MenuInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.menu.ComboInf;
import com.centerm.model.menu.MenuInf;
import com.centerm.service.menu.IMenuServiceImpl;
import com.centerm.utils.BeanUtil;

@Service("menuService")
@Transactional
public class MenuServiceImpl implements IMenuServiceImpl{
	
	private Logger logger = Logger.getLogger(this.getClass());

	private MenuInfMapper menuMapper;
	
	private InventoryInfMapper inventoryMapper;

	public InventoryInfMapper getInventoryMapper() {
		return inventoryMapper;
	}
	@Autowired
	public void setInventoryMapper(InventoryInfMapper inventoryMapper) {
		this.inventoryMapper = inventoryMapper;
	}
	
	public MenuInfMapper getMenuMapper() {
		return menuMapper;
	}
	@Autowired
	public void setMenuMapper(MenuInfMapper menuMapper) {
		this.menuMapper = menuMapper;
	}
	
	public List<MenuInf> list(MenuInf menu, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(menu);
		map.put("page", page);
		return menuMapper.query(map);
	}
	
	public int del(MenuInf menu){
		//校验是否在套餐中使用 
		List<ComboInf> combos = menuMapper.isUsedByCombo(menu.getProductId());
		if (combos.size() > 0) {
			String comboNames = "该菜品在套餐";
			for (ComboInf combo : combos) {
				comboNames += "【"+combo.getProductName()+"】,";
			}
			comboNames = comboNames.substring(0, comboNames.length()-1)+"中被使用,无法删除";
			throw new BusinessException(comboNames);
		}
		return menuMapper.updateByPrimaryKeySelective(menu);
		//inventoryMapper.deleteByPrimaryKey(menu.getProductId());
	}
	
	public int add(MenuInf menu){
		logger.info("=====添加单品开始");
		//唯一性校验
		int num = menuMapper.isNameExisted(menu);
		if (num > 0) {
			logger.info("=====单品名已存在:"+menu.getProductName());
			throw new BusinessException("菜品名已存在");
		}
		//设置优先级
		int maxPriority = menuMapper.queryMaxPriority(menu.getMchntCd());
		menu.setPriority(maxPriority+1);
		
		inventoryMapper.insert(menu.getInventory());
		menuMapper.insert(menu);
		logger.info("=====添加单品结束");
		return 0;
	}
	
	public int update(MenuInf menu){
		//唯一性校验
		int num = menuMapper.isNameExisted(menu);
		if (num > 0) {
			throw new BusinessException("菜品名已存在");
		}
		inventoryMapper.updateByPrimaryKeySelective(menu.getInventory());
		return menuMapper.updateByPrimaryKeySelective(menu);
	}
}
