package com.centerm.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.mchnt.FrchseInfMapper;
import com.centerm.dao.mchnt.MchntAuditInfMapper;
import com.centerm.dao.mchnt.MchntInfMapper;
import com.centerm.dao.menu.MenuTypeInfMapper;
import com.centerm.dao.menu.MenuVersionInfMapper;
import com.centerm.dao.sys.FunctionInfMapper;
import com.centerm.dao.sys.ParamInfMapper;
import com.centerm.dao.sys.SysMenuInfMapper;
import com.centerm.dao.sys.UserInfMapper;
import com.centerm.exception.BusinessException;
import com.centerm.model.mchnt.FrchseInf;
import com.centerm.model.mchnt.MchntAuditInf;
import com.centerm.model.mchnt.MchntInf;
import com.centerm.model.menu.MenuTypeInf;
import com.centerm.model.menu.MenuVersionInf;
import com.centerm.model.sys.FunctionInf;
import com.centerm.model.sys.LoginUser;
import com.centerm.model.sys.ParamInf;
import com.centerm.model.sys.RoleInf;
import com.centerm.model.sys.SysMenuInf;
import com.centerm.model.sys.UserInf;
import com.centerm.model.sys.UserInfo;
import com.centerm.service.sys.ILoginService;
import com.centerm.utils.DateUtils;
import com.centerm.utils.MD5;
import com.centerm.utils.StringUtils;

@Service("loginService")
@Transactional
public class LoginServiceImpl implements ILoginService {

	private UserInfMapper userMapper;
	private MchntInfMapper mchntMapper;
	private FunctionInfMapper functionMapper;
	private SysMenuInfMapper menuMapper;
	private MenuTypeInfMapper menuTypeMapper;
	private ParamInfMapper paramMapper;
	private FrchseInfMapper frchseInfMapper;
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
	public FrchseInfMapper getFrchseInfMapper() {
		return frchseInfMapper;
	}
	@Autowired
	public void setFrchseInfMapper(FrchseInfMapper frchseInfMapper) {
		this.frchseInfMapper = frchseInfMapper;
	}
	private GetSequenceService getSequenceService;

	public GetSequenceService getGetSequenceService() {
		return getSequenceService;
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
	public FunctionInfMapper getFunctionMapper() {
		return functionMapper;
	}
	@Autowired
	public void setFunctionMapper(FunctionInfMapper functionMapper) {
		this.functionMapper = functionMapper;
	}
	public SysMenuInfMapper getMenuMapper() {
		return menuMapper;
	}
	@Autowired
	public void setMenuMapper(SysMenuInfMapper menuMapper) {
		this.menuMapper = menuMapper;
	}
	public MchntInfMapper getMchntMapper() {
		return mchntMapper;
	}
	@Autowired
	public void setMchntMapper(MchntInfMapper mchntMapper) {
		this.mchntMapper = mchntMapper;
	}
	public UserInfMapper getUserMapper() {
		return userMapper;
	}
	@Autowired
	public void setUserMapper(UserInfMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public LoginUser login(String userId, String passwd) throws Exception {
		//登录
		UserInf user = userMapper.selectByPrimaryKey(userId);
		if(null==user) {
			throw new BusinessException("账号不存在");
		}
		if(!(new MD5().getMD5Str(passwd)).equals(user.getPasswd())) {
			throw new BusinessException("密码不正确");
		}
		LoginUser loginUser = new LoginUser();
		//设置用户信息
		UserInfo userInfo = new UserInfo();
		/*if (user.getStatus().equals("1")) {
			throw new BusinessException("该账号未激活");
		}*/
		RoleInf role = new RoleInf();
		if (user.getRole().equals("superadmin")) {
			userInfo.setUserId("0");
			userInfo.setUserName("超级管理员");
			role.setRoleId("c888cf40-ad23-409b-9ac0-c3d525c37cde");
		}else if(user.getRole().equals("merchant")){
			//查找商户信息
			MchntInf mchnt = mchntMapper.selectByUserId(userId);
			userInfo.setMchntCd(mchnt.getMchntCd());
			userInfo.setUserName(mchnt.getUserName());
			userInfo.setUserId(mchnt.getUserId());
			role.setRoleId("7cba832b-3fae-4c1c-97de-e2b91495afb8");
		}else if(user.getRole().equals("pendinguser")){
			//查找商户信息
			MchntInf mchnt = mchntMapper.selectByUserId(userId);
			userInfo.setMchntCd(mchnt.getMchntCd());
			userInfo.setUserName(mchnt.getUserName());
			userInfo.setUserId(mchnt.getUserId());
			role.setRoleId("f6d2c317-96fc-486e-8476-5fb609efa78f");
		}else if(user.getRole().equals("franchise")){
			//查找总部信息
			FrchseInf frchse = frchseInfMapper.selectByUserId(userId);
			userInfo.setMchntCd(frchse.getFrchseCd());
			userInfo.setUserName(frchse.getUserName());
			userInfo.setUserId(frchse.getUserId());
			role.setRoleId("2467498d-3ab1-4d7a-971b-6cbf80ea8880");
		}else {
			throw new BusinessException("只有商户管理员和总部账号可以登录系统");
		}
		
		List<RoleInf> roles = new ArrayList<RoleInf>();
		roles.add(role);
		//获取菜单权限
		List<SysMenuInf> menus = menuMapper.selectByRole(role);
		List<SysMenuInf> primaryMenus = buildMenus(menus);
		//获取功能权限
		List<FunctionInf> functions = functionMapper.selectByRole(role);
		
		loginUser.setRoles(roles);
		loginUser.setUserInfo(userInfo);
		loginUser.setMenus(primaryMenus);
		loginUser.setFunctions(functions);
		return loginUser;
	}
	/**
	 * 构造菜单
	 * @param menus
	 * @return
	 */
	private List<SysMenuInf> buildMenus(List<SysMenuInf> menus){
		List<SysMenuInf> primaryMenus = new ArrayList<SysMenuInf>();
		for (SysMenuInf menu : menus) {
			if (StringUtils.isNull(menu.getParentMenuId())) {
				List<SysMenuInf> subMenus = getSubMenus(menu.getMenuId(), menus);
				menu.setSubMenus(subMenus);
				primaryMenus.add(menu);
			} 
		}
		return primaryMenus;
	}
	/**
	 * 获取对应子菜单
	 * @param parentId
	 * @param menus
	 * @return
	 */
	private List<SysMenuInf> getSubMenus(String parentId, List<SysMenuInf> menus){
		List<SysMenuInf> subMenus = new ArrayList<SysMenuInf>();
		for (SysMenuInf menu : menus) {
			if (!StringUtils.isNull(menu.getParentMenuId()) && menu.getParentMenuId().equals(parentId)) {
				subMenus.add(menu);
			}
		}
		return subMenus;
	}
	@Override
	public LoginUser register(MchntInf mchnt, String passwd) throws Exception {
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
		//获取登录信息
		return login(mchnt.getUserId(), passwd);
	}
}
