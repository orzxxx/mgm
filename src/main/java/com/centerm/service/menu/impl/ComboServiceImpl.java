package com.centerm.service.menu.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.menu.ChildComboInfMapper;
import com.centerm.dao.menu.ChildComboTypeInfMapper;
import com.centerm.dao.menu.ComboDetailInfMapper;
import com.centerm.dao.menu.ComboInfMapper;
import com.centerm.dao.menu.InventoryInfMapper;
import com.centerm.dao.menu.MenuTypeInfMapper;
import com.centerm.dao.menu.MenuVersionInfMapper;
import com.centerm.dao.menu.ProductAttrInfMapper;
import com.centerm.dao.menu.ProductAttrTypeInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.menu.ChildComboInf;
import com.centerm.model.menu.ChildComboTypeInf;
import com.centerm.model.menu.ComboInf;
import com.centerm.model.menu.MenuTypeInf;
import com.centerm.model.menu.ProductAttrInf;
import com.centerm.model.menu.ProductAttrTypeInf;
import com.centerm.service.menu.IComboService;
import com.centerm.service.sys.impl.GetSequenceService;
import com.centerm.service.sys.impl.SysLogService;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.StringUtils;

@Service("comboService")
@Transactional
public class ComboServiceImpl implements IComboService{
	
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ComboInfMapper comboMapper;
	@Autowired
	private ComboDetailInfMapper comboDetailMapper;
	@Autowired
	private InventoryInfMapper inventoryMapper;
	@Autowired
	private MenuTypeInfMapper menuTypeMapper;
	@Autowired
	private GetSequenceService getSequenceService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private MenuVersionInfMapper menuVersionMapper;
	@Autowired
	private ChildComboTypeInfMapper childComboTypeMapper;
	@Autowired
	private ChildComboInfMapper childComboMapper;
	@Autowired
	private ProductAttrTypeInfMapper productAttrTypeMapper;
	@Autowired
	private ProductAttrInfMapper productAttrMapper;
	
	
	public List<ComboInf> list(ComboInf combo, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(combo);
		map.put("page", page);
		return comboMapper.query(map);
	}
	
	public void del(ComboInf combo){
		logger.info("=====删除套餐开始:"+combo.getProductId());
		//inventoryMapper.deleteByPrimaryKey(combo.getProductId());
		deletePackages(combo.getProductId());//删除组合
		comboMapper.updateByPrimaryKeySelective(combo);
		comboDetailMapper.deleteByComboId(combo.getProductId());
		menuVersionMapper.versionIncrement(combo.getMchntCd());//菜单版本自增
		//日志
		sysLogService.add("ComboServiceImpl.del", new String[]{"tbl_bkms_menu_combo_inf","tbl_bkms_menu_combo_detail_inf"}, combo, SysLogService.DELETE);
		logger.info("=====删除套餐结束:"+combo.getProductId());
	}
	
	public void add(ComboInf combo, List<ChildComboTypeInf> childComboTypes){
		logger.info("=====添加套餐开始:"+combo.getProductId());
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
			logger.info("=====套餐名已存在:"+combo.getProductName());
			throw new BusinessException("套餐名已存在");
		}
		//设置优先级
		int maxPriority = comboMapper.queryMaxPriority(combo.getMchntCd());
		combo.setPriority(maxPriority+1);
		logger.info("=====套餐优先级:"+combo.getPriority());
		//详情拼接
		String productDetail = "";
		for (ChildComboTypeInf childComboType : childComboTypes) {
			String childNames = "";
			for (ChildComboInf childCombo : childComboType.getChildCombos()) {
				//只拼接组合
				if (childCombo.getExchangeProductFlag() != 1) {
					childNames += childCombo.getSingleName() + "/";
				}
			}
			productDetail += childNames.substring(0, childNames.length()-1) + "+";
		}
		combo.setProductDetail(productDetail.substring(0, productDetail.length()-1));
		inventoryMapper.insert(combo.getInventory());
		comboMapper.insert(combo);
		menuVersionMapper.versionIncrement(combo.getMchntCd());//菜单版本自增
		//comboDetailMapper.insertbatch(comboDetails);
		//套餐组合
		List<ProductAttrInf> attrs = new LinkedList<ProductAttrInf>();
		int typeCount = 0;
		if (childComboTypes != null && childComboTypes.size() != 0) {
			for (ChildComboTypeInf childComboType : childComboTypes) {
				childComboType.setProductId(combo.getProductId());
				childComboType.setPriority(typeCount++);
				childComboType.setMchntCd(combo.getMchntCd());
				//判断能否换购
				childComboType.setExchangeFlag(0);
				for (ChildComboInf childComboInf :  childComboType.getChildCombos()) {
					if (childComboInf.getExchangeProductFlag() == 1) {
						childComboType.setExchangeFlag(1);
						break;
					}
				}
				childComboTypeMapper.insert(childComboType);
				//组合 换购
				int childCount = 0;
				for (ChildComboInf childCombo : childComboType.getChildCombos()) {
					//设置typeId
					childCombo.setComboTypeId(childComboType.getId());
					childCombo.setPriority(childCount++);
					childComboMapper.insert(childCombo);
					//套餐单品属性
					List<ProductAttrTypeInf> productAttrTypes = childCombo.getProductAttrTypes();
					if (productAttrTypes != null && productAttrTypes.size() != 0) {
						for (ProductAttrTypeInf attrType : productAttrTypes) {
							attrType.setProductId(childCombo.getId()+"");
							productAttrTypeMapper.insert(attrType);
							for (ProductAttrInf attr : attrType.getProductAttrs()) {
								//设置typeId
								attr.setAttrTypeId(attrType.getAttrTypeId());
							}
							attrs.addAll(attrType.getProductAttrs());
						}
						
					}
				}
			}
		}
		if (attrs != null && attrs.size() > 0) {
			productAttrMapper.insertbatch(attrs);
		}
		//日志
		sysLogService.add("ComboServiceImpl.add", new String[]{"tbl_bkms_menu_combo_inf"}, combo, SysLogService.INSERT);
		logger.info("=====添加套餐结束:"+combo.getProductId());
	}
	
	public void update(ComboInf combo, List<ChildComboTypeInf> childComboTypes) {
		logger.info("=====更新套餐开始:" + combo.getProductId());
		// 唯一性校验
		int num = comboMapper.isNameExisted(combo);
		if (num > 0) {
			logger.info("=====套餐名已存在:" + combo.getProductName());
			throw new BusinessException("套餐名已存在");
		}

		// 详情拼接
		String productDetail = "";
		for (ChildComboTypeInf childComboType : childComboTypes) {
			String childNames = "";
			for (ChildComboInf childCombo : childComboType.getChildCombos()) {
				// 只拼接组合
				if (childCombo.getExchangeProductFlag() != 1) {
					childNames += childCombo.getSingleName() + "/";
				}
			}
			productDetail += childNames.substring(0, childNames.length() - 1)
					+ "+";
		}
		combo.setProductDetail(productDetail.substring(0, productDetail.length()-1));
		// 删除原套餐组合
		deletePackages(combo.getProductId());
		// 套餐组合
		int typeCount = 0;
		List<ProductAttrInf> attrs = new LinkedList<ProductAttrInf>();
		if (childComboTypes != null && childComboTypes.size() != 0) {
			for (ChildComboTypeInf childComboType : childComboTypes) {
				childComboType.setProductId(combo.getProductId());
				childComboType.setPriority(typeCount++);
				childComboType.setMchntCd(combo.getMchntCd());
				// 判断能否换购
				childComboType.setExchangeFlag(0);
				for (ChildComboInf childComboInf : childComboType
						.getChildCombos()) {
					if (childComboInf.getExchangeProductFlag() == 1) {
						childComboType.setExchangeFlag(1);
						break;
					}
				}
				childComboTypeMapper.insert(childComboType);
				// 组合 换购
				int childCount = 0;
				for (ChildComboInf childCombo : childComboType.getChildCombos()) {
					// 设置typeId
					childCombo.setPriority(childCount++);
					childCombo.setComboTypeId(childComboType.getId());
					childComboMapper.insert(childCombo);
					// 套餐单品属性
					List<ProductAttrTypeInf> productAttrTypes = childCombo
							.getProductAttrTypes();
					if (productAttrTypes != null
							&& productAttrTypes.size() != 0) {
						for (ProductAttrTypeInf attrType : productAttrTypes) {
							attrType.setProductId(childCombo.getId() + "");
							attrType.setMchntCd(combo.getMchntCd());
							productAttrTypeMapper.insert(attrType);
							for (ProductAttrInf attr : attrType
									.getProductAttrs()) {
								// 设置typeId
								attr.setAttrTypeId(attrType.getAttrTypeId());
							}
							attrs.addAll(attrType.getProductAttrs());
						}

					}
				}
			}
		}
		if (attrs != null && attrs.size() > 0) {
			productAttrMapper.insertbatch(attrs);
		}

		inventoryMapper.updateByPrimaryKeySelective(combo.getInventory());
		comboMapper.updateByPrimaryKeySelective(combo);
		menuVersionMapper.versionIncrement(combo.getMchntCd());// 菜单版本自增
		// 日志
		sysLogService.add("ComboServiceImpl.update",
				new String[] { "tbl_bkms_menu_combo_inf" }, combo,
				SysLogService.UPDATE);
		logger.info("=====更新套餐结束:" + combo.getProductId());
	}
	private void deletePackages(String productId) {
		productAttrMapper.deleteByComboId(productId);//单品属性值
		productAttrTypeMapper.deleteByComboId(productId);//单品属性名
		childComboMapper.deleteByProductId(productId);//组合单品、换购
		childComboTypeMapper.deleteByProductId(productId);//组合类型
	}

	@Override
	public List<ChildComboTypeInf> geChildren(String comboId) {
		return childComboTypeMapper.getChildrenByComboId(comboId);
	}
}
