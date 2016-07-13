package com.centerm.service.menu.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.menu.ComboDetailInfMapper;
import com.centerm.dao.menu.ComboInfMapper;
import com.centerm.dao.menu.InventoryInfMapper;
import com.centerm.dao.menu.MenuTypeInfMapper;
import com.centerm.dao.menu.MenuVersionInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.menu.ComboDetailInf;
import com.centerm.model.menu.ComboInf;
import com.centerm.model.menu.MenuTypeInf;
import com.centerm.service.menu.IComboServiceImpl;
import com.centerm.service.sys.impl.GetSequenceService;
import com.centerm.service.sys.impl.SysLogService;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.StringUtils;

@Service("comboService")
@Transactional
public class ComboServiceImpl implements IComboServiceImpl{
	
	private Logger logger = Logger.getLogger(this.getClass());

	private ComboInfMapper comboMapper;
	
	private ComboDetailInfMapper comboDetailMapper;
	
	private InventoryInfMapper inventoryMapper;
	
	private MenuTypeInfMapper menuTypeMapper;
	
	private GetSequenceService getSequenceService;
	
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
	
	public void del(ComboInf combo){
		logger.info("=====删除套餐开始:"+combo.getProductId());
		//inventoryMapper.deleteByPrimaryKey(combo.getProductId());
		comboMapper.updateByPrimaryKeySelective(combo);
		comboDetailMapper.deleteByComboId(combo.getProductId());
		menuVersionMapper.versionIncrement(combo.getMchntCd());//菜单版本自增
		//日志
		sysLogService.add("ComboServiceImpl.del", new String[]{"tbl_bkms_menu_combo_inf","tbl_bkms_menu_combo_detail_inf"}, combo, SysLogService.DELETE);
		logger.info("=====删除套餐开始:"+combo.getProductId());
	}
	
	public void add(ComboInf combo){
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
		comboMapper.insert(combo);
		menuVersionMapper.versionIncrement(combo.getMchntCd());//菜单版本自增
		//日志
		sysLogService.add("ComboServiceImpl.add", new String[]{"tbl_bkms_menu_combo_inf","tbl_bkms_menu_combo_detail_inf"}, combo, SysLogService.INSERT);
		logger.info("=====添加套餐结束:"+combo.getProductId());
	}
	
	public void update(ComboInf combo){
		logger.info("=====更新套餐开始:"+combo.getProductId());
		//唯一性校验
		int num = comboMapper.isNameExisted(combo);
		if (num > 0) {
			logger.info("=====套餐名已存在:"+combo.getProductName());
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
		comboMapper.updateByPrimaryKeySelective(combo);
		menuVersionMapper.versionIncrement(combo.getMchntCd());//菜单版本自增
		//日志
		sysLogService.add("ComboServiceImpl.update", new String[]{"tbl_bkms_menu_combo_inf","tbl_bkms_menu_combo_detail_inf"}, combo, SysLogService.UPDATE);
		logger.info("=====更新套餐结束:"+combo.getProductId());
	}
	@Override
	public List<ComboDetailInf> getDetails(String comboId) {
		return comboDetailMapper.findByComboId(comboId);
	}
}
