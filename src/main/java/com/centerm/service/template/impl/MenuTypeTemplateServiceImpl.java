package com.centerm.service.template.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.template.MenuTypeTemplateInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.template.MenuTypeTemplateInf;
import com.centerm.service.template.IMenuTypeTemplateServiceImpl;
import com.centerm.utils.BeanUtil;

@Service("menuTypeTemplateService")
@Transactional
public class MenuTypeTemplateServiceImpl implements IMenuTypeTemplateServiceImpl{

	private MenuTypeTemplateInfMapper menuTypeTemplateMapper;

	public MenuTypeTemplateInfMapper getMenuTypeTemplateMapper() {
		return menuTypeTemplateMapper;
	}
	
	@Autowired
	public void setMenuTypeTemplateMapper(MenuTypeTemplateInfMapper menuTypeTemplateMapper) {
		this.menuTypeTemplateMapper = menuTypeTemplateMapper;
	}
	
	public List<MenuTypeTemplateInf> list(MenuTypeTemplateInf menuType, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(menuType);
		if (page != null) {
			map.put("page", page);
		}
		return menuTypeTemplateMapper.query(map);
	}
	
	public List<MenuTypeTemplateInf> tree(String mchntCd){
		return menuTypeTemplateMapper.tree(mchntCd);
	}	
	
	public int del(int id){
		return menuTypeTemplateMapper.deleteByPrimaryKey(id);
	}
	
	public int add(MenuTypeTemplateInf menuType){
		//唯一性校验
		int num = menuTypeTemplateMapper.isNameExisted(menuType);
		if (num > 0) {
			throw new BusinessException("分类名已存在");
		}
		//设置优先级
		int maxPriority = menuTypeTemplateMapper.queryMaxPriority(menuType.getMchntCd());
		menuType.setPriority(maxPriority+1);
		//添加库存表，预定
		return menuTypeTemplateMapper.insert(menuType);
	}
	
	public int update(MenuTypeTemplateInf menuType){
		//唯一性校验
		int num = menuTypeTemplateMapper.isNameExisted(menuType);
		if (num > 0) {
			throw new BusinessException("分类名已存在");
		}
		return menuTypeTemplateMapper.updateByPrimaryKeySelective(menuType);
	}
}
