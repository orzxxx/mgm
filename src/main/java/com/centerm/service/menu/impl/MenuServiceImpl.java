package com.centerm.service.menu.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.menu.InventoryInfMapper;
import com.centerm.dao.menu.MenuInfMapper;
import com.centerm.dao.menu.MenuVersionInfMapper;
import com.centerm.dao.menu.ProductAttrInfMapper;
import com.centerm.dao.menu.ProductAttrTypeInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.menu.ComboInf;
import com.centerm.model.menu.MenuInf;
import com.centerm.model.menu.ProductAttrInf;
import com.centerm.model.menu.ProductAttrTypeInf;
import com.centerm.service.menu.IMenuServiceImpl;
import com.centerm.service.sys.impl.SysLogService;
import com.centerm.utils.BeanUtil;

@Service("menuService")
@Transactional
public class MenuServiceImpl implements IMenuServiceImpl{
	
	private Logger logger = Logger.getLogger(this.getClass());

	private MenuInfMapper menuMapper;
	
	private InventoryInfMapper inventoryMapper;
	
	private ProductAttrTypeInfMapper productAttrTypeMapper;
	
	private ProductAttrInfMapper productAttrMapper;
	
	private SysLogService sysLogService;
	
	private MenuVersionInfMapper menuVersionMapper;
	
	public MenuVersionInfMapper getMenuVersionMapper() {
		return menuVersionMapper;
	}
	@Autowired
	public void setMenuVersionMapper(MenuVersionInfMapper menuVersionMapper) {
		this.menuVersionMapper = menuVersionMapper;
	}
	public SysLogService getSysLogService() {
		return sysLogService;
	}
	@Autowired
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}
	public ProductAttrInfMapper getProductAttrMapper() {
		return productAttrMapper;
	}
	@Autowired
	public void setProductAttrMapper(ProductAttrInfMapper productAttrMapper) {
		this.productAttrMapper = productAttrMapper;
	}
	public ProductAttrTypeInfMapper getProductAttrTypeMapper() {
		return productAttrTypeMapper;
	}
	@Autowired
	public void setProductAttrTypeMapper(
			ProductAttrTypeInfMapper productAttrTypeMapper) {
		this.productAttrTypeMapper = productAttrTypeMapper;
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
	

	public void del(MenuInf menu){
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
		menuMapper.updateByPrimaryKeySelective(menu);
		//删除属性
		productAttrMapper.deleteByProductId(menu.getProductId());
		productAttrTypeMapper.deleteByProductId(menu.getProductId());
		//inventoryMapper.deleteByPrimaryKey(menu.getProductId());
		menuVersionMapper.versionIncrement(menu.getMchntCd());//菜单版本自增
		//日志
		sysLogService.add("MenuServiceImpl.del", new String[]{"tbl_bkms_menu_inf"}, menu, SysLogService.UPDATE);
		logger.info("=====删除单品结束:"+menu.getProductId());
		
	}
	
	public void add(MenuInf menu, List<ProductAttrTypeInf> productAttrTypes){
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
		//添加属性
		if (productAttrTypes != null && productAttrTypes.size() != 0) {
			List<ProductAttrInf> attrs = new LinkedList<ProductAttrInf>();
			for (ProductAttrTypeInf attrType : productAttrTypes) {
				attrType.setProductId(menu.getProductId());
				productAttrTypeMapper.insert(attrType);
				for (ProductAttrInf attr : attrType.getProductAttrs()) {
					//设置typeId
					attr.setAttrTypeId(attrType.getAttrTypeId());
				}
				attrs.addAll(attrType.getProductAttrs());
			}
			productAttrMapper.insertbatch(attrs);
		}
		menuVersionMapper.versionIncrement(menu.getMchntCd());//菜单版本自增
		//日志
		sysLogService.add("MenuServiceImpl.add", new String[]{"tbl_bkms_menu_inf","tbl_bkms_product_inventory"}, menu, SysLogService.INSERT);
		if (productAttrTypes != null && productAttrTypes.size() != 0) {
			sysLogService.add("MenuServiceImpl.add", new String[]{"tbl_bkms_product_attr_type_inf","tbl_bkms_product_attr_inf"}, productAttrTypes, SysLogService.INSERT);
		}
		logger.info("=====添加单品结束:"+menu.getProductId());
	}
	
	public void update(MenuInf menu, List<ProductAttrTypeInf> productAttrTypes){
		logger.info("=====修改单品开始:"+menu.getProductId());
		//唯一性校验
		int num = menuMapper.isNameExisted(menu);
		if (num > 0) {
			logger.info("=====单品名已存在:"+menu.getProductName());
			throw new BusinessException("菜品名已存在");
		}
		inventoryMapper.updateByPrimaryKeySelective(menu.getInventory());
		menuMapper.updateByPrimaryKeySelective(menu);
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
					//设置typeId
					attr.setAttrTypeId(attrType.getAttrTypeId());
				}
				attrs.addAll(attrType.getProductAttrs());
			}
			productAttrMapper.insertbatch(attrs);
		}
		menuVersionMapper.versionIncrement(menu.getMchntCd());//菜单版本自增
		//日志
		sysLogService.add("MenuServiceImpl.update", new String[]{"tbl_bkms_menu_inf","tbl_bkms_product_inventory"}, menu, SysLogService.UPDATE);
		if (productAttrTypes != null && productAttrTypes.size() != 0) {
			sysLogService.add("MenuServiceImpl.update", new String[]{"tbl_bkms_product_attr_type_inf","tbl_bkms_product_attr_inf"}, productAttrTypes, SysLogService.INSERT, "覆盖旧属性");
		}
		logger.info("=====修改单品结束:"+menu.getProductId());
	}
}
