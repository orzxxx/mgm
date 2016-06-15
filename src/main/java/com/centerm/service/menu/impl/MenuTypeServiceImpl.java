package com.centerm.service.menu.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.mchnt.FrchseMchntMapInfMapper;
import com.centerm.dao.menu.InventoryInfMapper;
import com.centerm.dao.menu.MenuInfMapper;
import com.centerm.dao.menu.MenuTypeInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.menu.InventoryInf;
import com.centerm.model.menu.MenuInf;
import com.centerm.model.menu.MenuTypeInf;
import com.centerm.service.menu.IMenuTypeServiceImpl;
import com.centerm.service.sys.impl.GetSequenceService;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.PropertyUtils;

@Service("menuTypeService")
@Transactional
public class MenuTypeServiceImpl implements IMenuTypeServiceImpl{

	private GetSequenceService getSequenceService;

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
		Map param = new HashMap();
		param.put("mchntCd", mchntCd);
		param.put("needCombo", needCombo);
		return menuTypeMapper.tree(param);
	}	
	
	public int del(int id){
		return menuTypeMapper.deleteByPrimaryKey(id);
	}
	
	public int add(MenuTypeInf menuType){
		//唯一性校验
		int num = menuTypeMapper.isNameExisted(menuType);
		if (num > 0) {
			throw new BusinessException("分类名已存在");
		}
		//设置优先级
		int maxPriority = menuTypeMapper.queryMaxPriority(menuType.getMchntCd());
		menuType.setPriority(maxPriority+1);
		//添加库存表，预定
		return menuTypeMapper.insert(menuType);
	}
	
	public int update(MenuTypeInf menuType){
		//唯一性校验
		int num = menuTypeMapper.isNameExisted(menuType);
		if (num > 0) {
			throw new BusinessException("分类名已存在");
		}
		return menuTypeMapper.updateByPrimaryKeySelective(menuType);
	}

	@Override
	public int addTree(List<MenuTypeInf> menuTypeTree) throws Exception {
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
		menuTypes.addAll(existedTypes);//包括所有数据
		
		//菜品校验
		for (MenuTypeInf menuType : menuTypes) {
			if (menuType.getMenus() != null && menuType.getMenus().size() != 0) {
				param.put("mchntCd", mchntCd);
				param.put("menutpId", menuType.getMenutpId());
				param.put("list", menuType.getMenus());
				List<MenuInf> menuResult = menuMapper.isNamesExisted(param);
				if (menuResult.size() > 0) {
					throw new BusinessException("分类【"+menuType.getMenutpName()+"】中" +
							"已存在菜品【"+menuResult.get(0).getProductName()+"】");
				}
			}
		}
		//添加菜品和库存
		List<MenuInf> menus = new ArrayList<MenuInf>();
		List<InventoryInf> inventorys = new ArrayList<InventoryInf>();
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
					
					menu.setProductId(getSequenceService.GetNewProductId(false));
					menu.setPriority(++menuMaxPriority);
					menu.setMenutpId(menuType.getMenutpId());
					
					InventoryInf inventory = new InventoryInf();
					inventory.setInventory(-1);
					inventory.setProductId(menu.getProductId());
					inventorys.add(inventory);
				}
				menus.addAll(menuList);
			}
		}
		if (menus.size() != 0) {
			menuMapper.insertbatch(menus);
		}
		if (inventorys.size() != 0) {
			inventoryMapper.insertbatch(inventorys);
		}
		return 0;
	}
}
