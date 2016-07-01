package com.centerm.service.menu.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.mchnt.FrchseMchntMapInfMapper;
import com.centerm.dao.menu.InventoryInfMapper;
import com.centerm.dao.menu.MenuInfMapper;
import com.centerm.dao.menu.MenuTypeInfMapper;
import com.centerm.dao.menu.ProductAttrInfMapper;
import com.centerm.dao.menu.ProductAttrTypeInfMapper;
import com.centerm.dao.template.ProductAttrTypeTemplateInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.menu.InventoryInf;
import com.centerm.model.menu.MenuInf;
import com.centerm.model.menu.MenuTypeInf;
import com.centerm.model.menu.ProductAttrInf;
import com.centerm.model.menu.ProductAttrTypeInf;
import com.centerm.model.template.ProductAttrTemplateInf;
import com.centerm.model.template.ProductAttrTypeTemplateInf;
import com.centerm.service.menu.IMenuTypeServiceImpl;
import com.centerm.service.sys.impl.GetSequenceService;
import com.centerm.service.sys.impl.SysLogService;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.PropertyUtils;

@Service("menuTypeService")
@Transactional
public class MenuTypeServiceImpl implements IMenuTypeServiceImpl{
	
	private Logger logger = Logger.getLogger(this.getClass());

	private GetSequenceService getSequenceService;
	
	private SysLogService sysLogService;

	public GetSequenceService getGetSequenceService() {
		return getSequenceService;
	}
	@Autowired
	public void setGetSequenceService(GetSequenceService getSequenceService) {
		this.getSequenceService = getSequenceService;
	}
	
	private MenuTypeInfMapper menuTypeMapper;
	
	private MenuInfMapper menuMapper;

	private InventoryInfMapper inventoryMapper;
	
	private FrchseMchntMapInfMapper frchseMchntMapInfMapper;
	
	private ProductAttrTypeInfMapper productAttrTypeMapper;
	
	private ProductAttrInfMapper productAttrMapper;
	
	private ProductAttrTypeTemplateInfMapper productAttrTypeTemplateMapper;
	
	public ProductAttrTypeTemplateInfMapper getProductAttrTypeTemplateMapper() {
		return productAttrTypeTemplateMapper;
	}
	
	public SysLogService getSysLogService() {
		return sysLogService;
	}
	@Autowired
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}
	@Autowired
	public void setProductAttrTypeTemplateMapper(
			ProductAttrTypeTemplateInfMapper productAttrTypeTemplateMapper) {
		this.productAttrTypeTemplateMapper = productAttrTypeTemplateMapper;
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

	public FrchseMchntMapInfMapper getFrchseMchntMapInfMapper() {
		return frchseMchntMapInfMapper;
	}
	@Autowired
	public void setFrchseMchntMapInfMapper(
			FrchseMchntMapInfMapper frchseMchntMapInfMapper) {
		this.frchseMchntMapInfMapper = frchseMchntMapInfMapper;
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

	public MenuTypeInfMapper getMenuTypeMapper() {
		return menuTypeMapper;
	}
	
	@Autowired
	public void setMenuTypeMapper(MenuTypeInfMapper menuTypeMapper) {
		this.menuTypeMapper = menuTypeMapper;
	}
	
	public List<MenuTypeInf> list(MenuTypeInf menuType, Page page) throws Exception{
		Map<String,Object> map = BeanUtil.bean2Map(menuType);
		if (page != null) {
			map.put("page", page);
		}
		return menuTypeMapper.query(map);
	}

	public List<MenuTypeInf> tree(String mchntCd, boolean needCombo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mchntCd", mchntCd);
		param.put("needCombo", needCombo);
		return menuTypeMapper.tree(param);
	}	
	
	public void del(int id){
		logger.info("=====删除分类开始:"+id);
		menuTypeMapper.deleteByPrimaryKey(id);
		//日志
		sysLogService.add("MenuTypeServiceImpl.del", "tbl_bkms_menu_type_inf", id, SysLogService.DELETE);
		logger.info("=====删除分类结束:"+id);
	}
	
	public void add(MenuTypeInf menuType){
		logger.info("=====添加分类开始:"+menuType.getMenutpId());
		//唯一性校验
		int num = menuTypeMapper.isNameExisted(menuType);
		if (num > 0) {
			logger.info("=====分类型名已存在:"+menuType.getMenutpName());
			throw new BusinessException("分类名已存在");
		}
		//设置优先级
		int maxPriority = menuTypeMapper.queryMaxPriority(menuType.getMchntCd());
		menuType.setPriority(maxPriority+1);
		logger.info("=====分类优先级:"+menuType.getPriority());
		menuTypeMapper.insert(menuType);
		//日志
		sysLogService.add("MenuTypeServiceImpl.add", "tbl_bkms_menu_type_inf", menuType, SysLogService.INSERT);
		logger.info("=====添加分类结束:"+menuType.getMenutpId());
	}
	
	public void update(MenuTypeInf menuType){
		logger.info("=====更新分类开始:"+menuType.getMenutpId());
		//唯一性校验
		int num = menuTypeMapper.isNameExisted(menuType);
		if (num > 0) {
			logger.info("=====分类型名已存在:"+menuType.getMenutpName());
			throw new BusinessException("分类名已存在");
		}
		//日志
		sysLogService.add("MenuTypeServiceImpl.update", "tbl_bkms_menu_type_inf", menuType, SysLogService.UPDATE);
		menuTypeMapper.updateByPrimaryKeySelective(menuType);
		logger.info("=====更新分类结束:"+menuType.getMenutpId());
	}

	@Override
	public void addTree(List<MenuTypeInf> menuTypeTree) throws Exception {
		logger.info("=====导入单品开始");
		//java.util.Arrays$ArrayList ==> java.util.ArrayList
		List<MenuTypeInf> menuTypes = new ArrayList<MenuTypeInf>();
		menuTypes.addAll(menuTypeTree);
		
		if (menuTypes.size() == 0) {
			throw new BusinessException("导入数据不存在");
		}
		//分类校验
		String mchntCd = menuTypes.get(0).getMchntCd();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("mchntCd", mchntCd);
		param.put("list", menuTypes);
		List<MenuTypeInf> result = menuTypeMapper.isNamesExisted(param);
		List<MenuTypeInf> existedTypes = new ArrayList<MenuTypeInf>();
		if (result.size() > 0) {
			//名称已存在则排除掉,不添加
			Iterator<MenuTypeInf> it = menuTypes.iterator();
			while (it.hasNext()) {
				MenuTypeInf menuType = it.next();
				for (MenuTypeInf data : result) {
					if (data.getMenutpName().equals(menuType.getMenutpName())) {
						menuType.setMenutpId(data.getMenutpId());
						existedTypes.add(menuType);
						it.remove();
					}
				}
			}
		}
		//添加分类
		if (menuTypes != null && menuTypes.size() > 0) {
			int maxPriority = menuTypeMapper.queryMaxPriority(mchntCd);
			for (MenuTypeInf menuType : menuTypes) {
				menuType.setMenutpId(getSequenceService.CreateNewMenutpId(false));
				menuType.setPriority(++maxPriority);
			}
			
			menuTypeMapper.insertbatch(menuTypes);
		}
		sysLogService.add("MenuTypeServiceImpl.addTree", "tbl_bkms_menu_type_inf", menuTypes, SysLogService.INSERT);
		menuTypes.addAll(existedTypes);//包括所有数据
		
		//菜品校验
		for (MenuTypeInf menuType : menuTypes) {
			if (menuType.getMenus() != null && menuType.getMenus().size() != 0) {
				param.put("mchntCd", mchntCd);
				param.put("menutpId", menuType.getMenutpId());
				param.put("list", menuType.getMenus());
				List<MenuInf> menuResult = menuMapper.isNamesExisted(param);
				if (menuResult.size() > 0) {
					logger.info("分类【"+menuType.getMenutpName()+"】中" +
							"已存在菜品【"+menuResult.get(0).getProductName()+"】");
					throw new BusinessException("分类【"+menuType.getMenutpName()+"】中" +
							"已存在菜品【"+menuResult.get(0).getProductName()+"】");
				}
			}
		}
		//添加菜品和库存
		List<MenuInf> menus = new ArrayList<MenuInf>();
		List<InventoryInf> inventorys = new ArrayList<InventoryInf>();
		List<ProductAttrInf> attrs = new LinkedList<ProductAttrInf>();
		int menuMaxPriority = menuMapper.queryMaxPriority(mchntCd);
		for (MenuTypeInf menuType : menuTypes) {
			List<MenuInf> menuList = menuType.getMenus();
			if (menuList != null && menuList.size() != 0) {
				String filePath = PropertyUtils.getProperties("imageSavePath");
				String frchseCd = frchseMchntMapInfMapper.selectFrchseCdByMchntCd(mchntCd);
				for (MenuInf menu : menuList) {
					//复制总部图片
					String pictureName = menu.getPictureLink();
					File srcFile = new File(filePath+"/"+frchseCd+"/"+pictureName);
					File destFile = new File(filePath+"/"+mchntCd+"/"+pictureName);
					FileUtils.copyFile(srcFile, destFile);
					
					String tmplProductId = menu.getProductId();
					
					menu.setProductId(getSequenceService.GetNewProductId(false));
					menu.setPriority(++menuMaxPriority);
					menu.setMenutpId(menuType.getMenutpId());
					menu.setPackingBoxNum(0);
					
					InventoryInf inventory = new InventoryInf();
					inventory.setInventory(-1);
					inventory.setProductId(menu.getProductId());
					inventorys.add(inventory);
					//添加属性
					List<ProductAttrTypeTemplateInf> attrTypes = productAttrTypeTemplateMapper.queryByProductId(tmplProductId);
					if (attrTypes != null && attrTypes.size() != 0) {
						for (ProductAttrTypeTemplateInf attrType : attrTypes) {
							ProductAttrTypeInf pAttrType = new ProductAttrTypeInf();
							pAttrType.setAttrTypeName(attrType.getAttrTypeName());
							pAttrType.setProductId(menu.getProductId());
							pAttrType.setMchntCd(mchntCd);
							pAttrType.setProductId(menu.getProductId());
							productAttrTypeMapper.insert(pAttrType);
							for (ProductAttrTemplateInf attr : attrType.getProductAttrs()) {
								ProductAttrInf pAttr = new ProductAttrInf();
								//设置typeId
								pAttr.setAttrTypeId(pAttrType.getAttrTypeId());
								pAttr.setAttrName(attr.getAttrName());
								pAttr.setAttrPrice(attr.getAttrPrice());
								attrs.add(pAttr);
							}
						}
					}
				}
				menus.addAll(menuList);
			}
		}
		if (attrs.size() != 0) {
			sysLogService.add("MenuTypeServiceImpl.addTree", new String[]{"tbl_bkms_product_attr_type_inf","tbl_bkms_product_attr_inf"}, attrs, SysLogService.INSERT);
			productAttrMapper.insertbatch(attrs);
		}
		if (menus.size() != 0) {
			sysLogService.add("MenuTypeServiceImpl.addTree", new String[]{"tbl_bkms_menu_inf","tbl_bkms_product_inventory"}, menus, SysLogService.INSERT);
			menuMapper.insertbatch(menus);
		}
		if (inventorys.size() != 0) {
			inventoryMapper.insertbatch(inventorys);
		}
		logger.info("=====导入单品结束");
	}
}
