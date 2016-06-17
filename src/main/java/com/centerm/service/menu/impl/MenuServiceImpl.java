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
import com.centerm.service.sys.impl.SysLogService;
import com.centerm.utils.BeanUtil;

@Service("menuService")
@Transactional
public class MenuServiceImpl implements IMenuServiceImpl{
	
	private Logger logger = Logger.getLogger(this.getClass());

	private MenuInfMapper menuMapper;
	
	private InventoryInfMapper inventoryMapper;
	
	private SysLogService sysLogService;
	
	public SysLogService getSysLogService() {
		return sysLogService;
	}
	@Autowired
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}
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
		logger.info("=====删除单品开始:"+menu.getProductId());
		//校验是否在套餐中使用 
		List<ComboInf> combos = menuMapper.isUsedByCombo(menu.getProductId());
		if (combos.size() > 0) {
			String comboNames = "该菜品在套餐";
			for (ComboInf combo : combos) {
				comboNames += "【"+combo.getProductName()+"】,";
			}
			comboNames = comboNames.substring(0, comboNames.length()-1)+"中被使用,无法删除";
			logger.info("=====删除单品失败:"+comboNames);
			throw new BusinessException(comboNames);
		}
		//inventoryMapper.deleteByPrimaryKey(menu.getProductId());
		menuMapper.updateByPrimaryKeySelective(menu);
		//日志
		sysLogService.add("MenuServiceImpl.del", new String[]{"tbl_bkms_menu_inf"}, menu, SysLogService.UPDATE);
		logger.info("=====删除单品结束:"+menu.getProductId());
		return 0;
	}
	
	public int add(MenuInf menu){
		logger.info("=====添加单品开始:"+menu.getProductId());
		//唯一性校验
		int num = menuMapper.isNameExisted(menu);
		if (num > 0) {
			logger.info("=====单品名已存在:"+menu.getProductName());
			throw new BusinessException("菜品名已存在");
		}
		//设置优先级
		int maxPriority = menuMapper.queryMaxPriority(menu.getMchntCd());
		menu.setPriority(maxPriority+1);
		logger.info("=====单品优先级:"+menu.getPriority());
		
		inventoryMapper.insert(menu.getInventory());
		menuMapper.insert(menu);
		//日志
		sysLogService.add("MenuServiceImpl.add", new String[]{"tbl_bkms_menu_inf","tbl_bkms_product_inventory"}, menu, SysLogService.INSERT);
		logger.info("=====添加单品结束:"+menu.getProductId());
		return 0;
	}
	
	public int update(MenuInf menu){
		logger.info("=====修改单品开始:"+menu.getProductId());
		//唯一性校验
		int num = menuMapper.isNameExisted(menu);
		if (num > 0) {
			logger.info("=====单品名已存在:"+menu.getProductName());
			throw new BusinessException("菜品名已存在");
		}
		inventoryMapper.updateByPrimaryKeySelective(menu.getInventory());
		menuMapper.updateByPrimaryKeySelective(menu);
		//日志
		sysLogService.add("MenuServiceImpl.update", new String[]{"tbl_bkms_menu_inf","tbl_bkms_product_inventory"}, menu, SysLogService.UPDATE);
		logger.info("=====修改单品结束:"+menu.getProductId());
		return 0;
	}
}
