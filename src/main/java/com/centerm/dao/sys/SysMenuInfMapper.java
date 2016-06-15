package com.centerm.dao.sys;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.sys.SysMenuInf;
import com.centerm.model.sys.RoleInf;

public interface SysMenuInfMapper extends BaseMapper<SysMenuInf>{
    List<SysMenuInf> selectByRole(RoleInf role);
}