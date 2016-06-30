package com.centerm.service.template.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.menu.InventoryInfMapper;
import com.centerm.dao.menu.ProductAttrInfMapper;
import com.centerm.dao.menu.ProductAttrTypeInfMapper;
import com.centerm.dao.template.MenuTemplateInfMapper;
import com.centerm.dao.template.ProductAttrTemplateInfMapper;
import com.centerm.dao.template.ProductAttrTypeTemplateInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.menu.ProductAttrInf;
import com.centerm.model.menu.ProductAttrTypeInf;
import com.centerm.model.template.MenuTemplateInf;
import com.centerm.service.template.IMenuTemplateServiceImpl;
import com.centerm.utils.BeanUtil;

@Service("menuTemplateService")
@Transactional
public class MenuTemplateServiceImpl implements IMenuTemplateServiceImpl{

	private MenuTemplateInfMapper menuTemplateMapper;

	private InventoryInfMapper inventoryMapper;
	
	private ProductAttrTypeTemplateInfMapper productAttrTypeMapper;
	
	private ProductAttrTemplateInfMapper productAttrMapper;

	public ProductAttrTypeTemplateInfMapper getProductAttrTypeMapper() {
		return productAttrTypeMapper;
	}
	@Autowired
	public void setProductAttrTypeMapper(
			ProductAttrTypeTemplateInfMapper productAttrTypeMapper) {
		this.productAttrTypeMapper = productAttrTypeMapper;
	}
	public ProductAttrTemplateInfMapper getProductAttrMapper() {
		return productAttrMapper;
	}
	@Autowired
	public void setProductAttrMapper(ProductAttrTemplateInfMapper productAttrMapper) {
		this.productAttrMapper = productAttrMapper;
	}
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
	
	public void del(MenuTemplateInf menu){
		menuTemplateMapper.updateByPrimaryKeySelective(menu);
		//删除属性
		productAttrMapper.deleteByProductId(menu.getProductId());
		productAttrTypeMapper.deleteByProductId(menu.getProductId());
	}
	
	public void add(MenuTemplateInf menu,
			List<ProductAttrTypeInf> productAttrTypes) {
		// 唯一性校验
		int num = menuTemplateMapper.isNameExisted(menu);
		if (num > 0) {
			throw new BusinessException("菜品名已存在");
		}
		// 设置优先级
		int maxPriority = menuTemplateMapper
				.queryMaxPriority(menu.getMchntCd());
		menu.setPriority(maxPriority + 1);
		menuTemplateMapper.insert(menu);
		// 添加属性
		if (productAttrTypes != null && productAttrTypes.size() != 0) {
			List<ProductAttrInf> attrs = new LinkedList<ProductAttrInf>();
			for (ProductAttrTypeInf attrType : productAttrTypes) {
				attrType.setProductId(menu.getProductId());
				productAttrTypeMapper.insert(attrType);
				for (ProductAttrInf attr : attrType.getProductAttrs()) {
					// 设置typeId
					attr.setAttrTypeId(attrType.getAttrTypeId());
				}
				attrs.addAll(attrType.getProductAttrs());
			}
			productAttrMapper.insertbatch(attrs);
		}
	}
	
	public void update(MenuTemplateInf menu, List<ProductAttrTypeInf> productAttrTypes) {
		// 唯一性校验
		int num = menuTemplateMapper.isNameExisted(menu);
		if (num > 0) {
			throw new BusinessException("菜品名已存在");
		}
		menuTemplateMapper.updateByPrimaryKeySelective(menu);
		//删除属性
		productAttrMapper.deleteByProductId(menu.getProductId());
		productAttrTypeMapper.deleteByProductId(menu.getProductId());
		// 添加属性
		if (productAttrTypes != null && productAttrTypes.size() != 0) {
			List<ProductAttrInf> attrs = new LinkedList<ProductAttrInf>();
			for (ProductAttrTypeInf attrType : productAttrTypes) {
				attrType.setProductId(menu.getProductId());
				productAttrTypeMapper.insert(attrType);
				for (ProductAttrInf attr : attrType.getProductAttrs()) {
					// 设置typeId
					attr.setAttrTypeId(attrType.getAttrTypeId());
				}
				attrs.addAll(attrType.getProductAttrs());
			}
			productAttrMapper.insertbatch(attrs);
		}
	}
}
