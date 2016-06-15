package com.centerm.model.sys;


import java.util.List;

public class LoginUser {
	private UserInfo userInfo;
	private List<SysMenuInf> menus;
	private List<FunctionInf> functions;
	private List<RoleInf> roles;

	public List<RoleInf> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleInf> roles) {
		this.roles = roles;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<SysMenuInf> getMenus() {
		return menus;
	}

	public void setMenus(List<SysMenuInf> menus) {
		this.menus = menus;
	}

	public List<FunctionInf> getFunctions() {
		return functions;
	}

	public void setFunctions(List<FunctionInf> functions) {
		this.functions = functions;
	}
	
	public boolean isSuperAdmin(){
		for (RoleInf role : roles) {
			if (role.getRoleId().equals("0")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isMchntAdmin(){
		if (isSuperAdmin()) {
			return false;
		}
		for (RoleInf role : roles) {
			if (role.getRoleId().equals("2")) {
				return true;
			}
		}
		return false;
	}
}
