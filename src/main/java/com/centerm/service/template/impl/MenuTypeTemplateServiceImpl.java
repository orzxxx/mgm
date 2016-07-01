package com.centerm.service.template.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.template.MenuTypeTemplateInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.template.MenuTypeTemplateInf;
import com.centerm.service.sys.impl.SysLogService;
import com.centerm.service.template.IMenuTypeTemplateServiceImpl;
import com.centerm.utils.BeanUtil;

@Service("menuTypeTemplateService")
@Transactional
public class MenuTypeTemplateServiceImpl implements IMenuTypeTemplateServiceImpl{
	
	private Logger logger = Logger.getLogger(this.getClass());

	private MenuTypeTemplateInfMapper menuTypeTemplateMapper;
	
	private SysLogService sysLogService;
	
	public SysLogService getSysLogService() {
		return sysLogService;
	}
	@Autowired
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

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
	
	public void del(int id){
		logger.info("=====删除分类模板开始:"+id);
		menuTypeTemplateMapper.deleteByPrimaryKey(id);
		//日志
		sysLogService.add("MenuTypeTemplateServiceImpl.del", "tbl_bkms_menu_type_template", id, SysLogService.DELETE);
		logger.info("=====删除分类模板开始:"+id);
	}
	
	public void add(MenuTypeTemplateInf menuType){
		logger.info("=====添加分类模板开始:"+menuType.getMenutpId());
		//唯一性校验
		int num = menuTypeTemplateMapper.isNameExisted(menuType);
		if (num > 0) {
			logger.info("=====分类型名已存在:"+menuType.getMenutpName());
			throw new BusinessException("分类名已存在");
		}
		//设置优先级
		int maxPriority = menuTypeTemplateMapper.queryMaxPriority(menuType.getMchntCd());
		menuType.setPriority(maxPriority+1);
		logger.info("=====分类优先级:"+menuType.getPriority());
		//添加库存表，预定
		menuTypeTemplateMapper.insert(menuType);
		//日志
		sysLogService.add("MenuTypeTemplateServiceImpl.add", "tbl_bkms_menu_type_template", menuType, SysLogService.INSERT);
		logger.info("=====添加分类模板结束:"+menuType.getMenutpId());
	}
	
	public void update(MenuTypeTemplateInf menuType){
		logger.info("=====更新分类模板开始:"+menuType.getMenutpId());
		//唯一性校验
		int num = menuTypeTemplateMapper.isNameExisted(menuType);
		if (num > 0) {
			logger.info("=====分类型名已存在:"+menuType.getMenutpName());
			throw new BusinessException("分类名已存在");
		}
		menuTypeTemplateMapper.updateByPrimaryKeySelective(menuType);
		//日志
		sysLogService.add("MenuTypeTemplateServiceImpl.update", "tbl_bkms_menu_type_template", menuType, SysLogService.UPDATE);
		logger.info("=====更新分类模板结束:"+menuType.getMenutpId());
	}
}
