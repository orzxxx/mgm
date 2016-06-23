package com.centerm.service.mchnt.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.base.Page;
import com.centerm.dao.mchnt.FrchseMchntMapInfMapper;
import com.centerm.dao.mchnt.MchntAuditInfMapper;
import com.centerm.dao.mchnt.MchntInfMapper;
import com.centerm.dao.menu.MenuTypeInfMapper;
import com.centerm.dao.menu.MenuVersionInfMapper;
import com.centerm.dao.sys.ParamInfMapper;
import com.centerm.dao.sys.UserInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.mchnt.FrchseMchntMapInf;
import com.centerm.model.mchnt.MchntAuditInf;
import com.centerm.model.mchnt.MchntInf;
import com.centerm.model.menu.MenuVersionInf;
import com.centerm.model.sys.UserInf;
import com.centerm.service.mchnt.IMerchantService;
import com.centerm.service.sys.impl.GetSequenceService;
import com.centerm.utils.BeanUtil;
import com.centerm.utils.MD5;
import com.centerm.utils.StringUtils;

@Service("merchantService")
@Transactional
public class MerchantServiceImpl implements IMerchantService{
	
	private UserInfMapper userMapper;
	private MchntInfMapper mchntMapper;
	private MenuTypeInfMapper menuTypeMapper;
	private ParamInfMapper paramMapper;
	private FrchseMchntMapInfMapper frchseMchntMapMapper; 
	private MchntAuditInfMapper mchntAuditInfMapper;
	private MenuVersionInfMapper menuVersionInfMapper;
	
	public MenuVersionInfMapper getMenuVersionInfMapper() {
		return menuVersionInfMapper;
	}
	@Autowired
	public void setMenuVersionInfMapper(MenuVersionInfMapper menuVersionInfMapper) {
		this.menuVersionInfMapper = menuVersionInfMapper;
	}
	
	public MchntAuditInfMapper getMchntAuditInfMapper() {
		return mchntAuditInfMapper;
	}
	@Autowired
	public void setMchntAuditInfMapper(MchntAuditInfMapper mchntAuditInfMapper) {
		this.mchntAuditInfMapper = mchntAuditInfMapper;
	}
	private GetSequenceService getSequenceService;

	public GetSequenceService getGetSequenceService() {
		return getSequenceService;
	}
	public FrchseMchntMapInfMapper getFrchseMchntMapMapper() {
		return frchseMchntMapMapper;
	}
	@Autowired
	public void setFrchseMchntMapMapper(FrchseMchntMapInfMapper frchseMchntMapMapper) {
		this.frchseMchntMapMapper = frchseMchntMapMapper;
	}
	@Autowired
	public void setGetSequenceService(GetSequenceService getSequenceService) {
		this.getSequenceService = getSequenceService;
	}
	
	public ParamInfMapper getParamMapper() {
		return paramMapper;
	}
	@Autowired
	public void setParamMapper(ParamInfMapper paramMapper) {
		this.paramMapper = paramMapper;
	}
	
	public MenuTypeInfMapper getMenuTypeMapper() {
		return menuTypeMapper;
	}
	@Autowired
	public void setMenuTypeMapper(MenuTypeInfMapper menuTypeMapper) {
		this.menuTypeMapper = menuTypeMapper;
	}
	public UserInfMapper getUserMapper() {
		return userMapper;
	}
	@Autowired
	public void setUserMapper(UserInfMapper userMapper) {
		this.userMapper = userMapper;
	}
	public MchntInfMapper getMchntMapper() {
		return mchntMapper;
	}
	@Autowired
	public void setMchntMapper(MchntInfMapper mchntMapper) {
		this.mchntMapper = mchntMapper;
	}
	
	public List<MchntInf> list(MchntInf mchnt, Page page) throws Exception{
		//模糊查询
		if(!StringUtils.isNull(mchnt.getMchntName())){
			mchnt.setMchntName("%"+mchnt.getMchntName()+"%");
		}
		if(!StringUtils.isNull(mchnt.getMchntCd())){
			mchnt.setMchntCd("%"+mchnt.getMchntCd()+"%");
		}
		Map<String,Object> map = BeanUtil.bean2Map(mchnt);
		map.put("page", page);
		return mchntMapper.query(map);
	}
	
	@Override
	public List<MchntInf> selectMchntByFrchseCd(String frchseCd, String mchntName, Page page) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("frchseCd", frchseCd);
		map.put("mchntName", mchntName);
		map.put("page", page);
		return frchseMchntMapMapper.selectMchntByFrchseCd(map);
	}
	
	public MchntInf get(String mchntCd){
		return mchntMapper.selectByPrimaryKey(mchntCd);
	}
	@Override
	public int update(MchntInf mchnt) {
		return mchntMapper.updateByPrimaryKeySelective(mchnt);
	}
	
	public int del(int id){
		return mchntMapper.deleteByPrimaryKey(id);
	}
	
	public int add(MchntInf mchnt, String frchseCd, String passwd){
		//用户名检测
		int num = mchntMapper.isUserIdExisted(mchnt);
		if (num > 0) {
			throw new BusinessException("手机号已被注册");
		}
		//添加商户信息
		mchnt.setStatus(2);
		mchnt.setMchntCd(getSequenceService.CreateNewMchntCd());
		mchntMapper.insert(mchnt);
		//添加菜单版本
		MenuVersionInf menuVer = new MenuVersionInf();
		menuVer.setMchntCd(mchnt.getMchntCd());
		menuVer.setMenuVersion(0);
		menuVersionInfMapper.insert(menuVer);
		//添加账号
		UserInf user = new UserInf();
		user.setStatus("0");
		user.setPasswd(new MD5().getMD5Str(passwd));
		user.setUserId(mchnt.getUserId());
		user.setRole("pendinguser");
		userMapper.insert(user);
		//添加审核表
		MchntAuditInf mchntAudit = new MchntAuditInf();
		mchntAudit.setMchntCd(mchnt.getMchntCd());
		mchntAudit.setAuditStatus(0);
		mchntAuditInfMapper.insert(mchntAudit);
		//总部关联
		FrchseMchntMapInf frchseMchntMap = new FrchseMchntMapInf();
		frchseMchntMap.setUuid(UUID.randomUUID().toString());
		frchseMchntMap.setStatus(0);
		frchseMchntMap.setFrchseCd(frchseCd);
		frchseMchntMap.setMchntCd(mchnt.getMchntCd());
		frchseMchntMapMapper.insert(frchseMchntMap);
		return 0;
	}
	@Override
	public void resetPwd(MchntInf mchnt, String oldPwd, String newPwd) {
		UserInf user = userMapper.selectByPrimaryKey(mchnt.getUserId());
		if(!(new MD5().getMD5Str(oldPwd)).equals(user.getPasswd())) {
			throw new BusinessException("旧密码不正确");
		}
		if(oldPwd.equals(newPwd)) {
			throw new BusinessException("新旧密码不能相同");
		}
		user.setPasswd(new MD5().getMD5Str(newPwd));
		userMapper.updateByPrimaryKeySelective(user);
		
	}
}
