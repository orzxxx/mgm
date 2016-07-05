package com.centerm.service.template.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.menu.InventoryInfMapper;
import com.centerm.dao.template.MenuTemplateInfMapper;
import com.centerm.dao.template.ProductAttrTemplateInfMapper;
import com.centerm.dao.template.ProductAttrTypeTemplateInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.menu.ProductAttrInf;
import com.centerm.model.menu.ProductAttrTypeInf;
import com.centerm.model.template.MenuTemplateInf;
import com.centerm.service.sys.impl.SysLogService;
import com.centerm.service.template.IMenuTemplateServiceImpl;
import com.centerm.utils.BeanUtil;

@Service("menuTemplateService")
@Transactional
public class MenuTemplateServiceImpl implements IMenuTemplateServiceImpl{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private MenuTemplateInfMapper menuTemplateMapper;

	private InventoryInfMapper inventoryMapper;
	
	private ProductAttrTypeTemplateInfMapper productAttrTypeMapper;
	
	private ProductAttrTemplateInfMapper productAttrMapper;
	
	private SysLogService sysLogService;
	
	public SysLogService getSysLogService() {
		return sysLogService;
	}
	@Autowired
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

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
		logger.info("=====删除单品模板开始:"+menu.getProductId());
		menuTemplateMapper.updateByPrimaryKeySelective(menu);
		//删除属性
		productAttrMapper.deleteByProductId(menu.getProductId());
		productAttrTypeMapper.deleteByProductId(menu.getProductId());
		//日志
		sysLogService.add("MenuTemplateServiceImpl.del", "tbl_bkms_menu_inf_template", menu, SysLogService.UPDATE);
		logger.info("=====删除单品模板结束:"+menu.getProductId());
	}
	
	public void add(MenuTemplateInf menu,
			List<ProductAttrTypeInf> productAttrTypes) {
		logger.info("=====添加单品模板开始:"+menu.getProductId());
		// 唯一性校验
		int num = menuTemplateMapper.isNameExisted(menu);
		if (num > 0) {
			logger.info("=====单品名已存在:"+menu.getProductName());
			throw new BusinessException("菜品名已存在");
		}
		// 设置优先级
		int maxPriority = menuTemplateMapper
				.queryMaxPriority(menu.getMchntCd());
		menu.setPriority(maxPriority + 1);
		logger.info("=====单品优先级:"+menu.getPriority());
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
		//日志
		sysLogService.add("MenuTemplateServiceImpl.add", "tbl_bkms_menu_inf_template", menu, SysLogService.INSERT);
		if (productAttrTypes != null && productAttrTypes.size() != 0) {
			sysLogService.add("MenuTemplateServiceImpl.add", new String[]{"tbl_bkms_product_attr_type_template","tbl_bkms_product_attr_template"}, productAttrTypes, SysLogService.INSERT);
		}
		logger.info("=====添加单品模板开始:"+menu.getProductId());
	}
	
	public void update(MenuTemplateInf menu, List<ProductAttrTypeInf> productAttrTypes) {
		logger.info("=====更新单品模板开始:"+menu.getProductId());
		// 唯一性校验
		int num = menuTemplateMapper.isNameExisted(menu);
		if (num > 0) {
			logger.info("=====单品名已存在:"+menu.getProductName());
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
		//日志
		sysLogService.add("MenuTemplateServiceImpl.update", "tbl_bkms_menu_inf_template", menu, SysLogService.UPDATE);
		if (productAttrTypes != null && productAttrTypes.size() != 0) {
			sysLogService.add("MenuTemplateServiceImpl.update", new String[]{"tbl_bkms_product_attr_type_template","tbl_bkms_product_attr_template"}, productAttrTypes, SysLogService.INSERT, "覆盖旧属性");
		}
		logger.info("=====更新单品模板开始:"+menu.getProductId());
	}
}
