package com.centerm.service.template.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.menu.InventoryInfMapper;
import com.centerm.dao.template.MenuTemplateInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.template.MenuTemplateInf;
import com.centerm.service.template.IMenuTemplateServiceImpl;
import com.centerm.utils.BeanUtil;

@Service("menuTemplateService")
@Transactional
public class MenuTemplateServiceImpl implements IMenuTemplateServiceImpl{

	private MenuTemplateInfMapper menuTemplateMapper;

	private InventoryInfMapper inventoryMapper;

	public InventoryInfMapper getInventoryMapper() {
		return inventoryMapper;
	}
	@Autowired
	public void setInventoryMapper(InventoryInfMapper inventoryMapper) {
		this.inventoryMapper = inventoryMapper;
	}
	
	public MenuTemplateInfMapper getMenuTemplateMapper() {
		return menuTemplateMapper;
	}
	@Autowired
	public void setMenuTemplateMapper(MenuTemplateInfMapper menuTemplateMapper) {
		this.menuTemplateMapper = menuTemplateMapper;
	}
	
	public List<MenuTemplateInf> list(MenuTemplateInf menu, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(menu);
		map.put("page", page);
		return menuTemplateMapper.query(map);
	}
	
	public int del(MenuTemplateInf menu){
		return menuTemplateMapper.updateByPrimaryKeySelective(menu);
	}
	
	public int add(MenuTemplateInf menu){
		//唯一性校验
		int num = menuTemplateMapper.isNameExisted(menu);
		if (num > 0) {
			throw new BusinessException("菜品名已存在");
		}
		//设置优先级
		int maxPriority = menuTemplateMapper.queryMaxPriority(menu.getMchntCd());
		menu.setPriority(maxPriority+1);
		return menuTemplateMapper.insert(menu);
	}
	
	public int update(MenuTemplateInf menu){
		//唯一性校验
		int num = menuTemplateMapper.isNameExisted(menu);
		if (num > 0) {
			throw new BusinessException("菜品名已存在");
		}
		return menuTemplateMapper.updateByPrimaryKeySelective(menu);
	}
}
