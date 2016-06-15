package com.centerm.dao.sys;

import com.centerm.model.sys.RoleInf;

public interface RoleInfMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(RoleInf record);

    int insertSelective(RoleInf record);

    RoleInf selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(RoleInf record);

    int updateByPrimaryKey(RoleInf record);
}