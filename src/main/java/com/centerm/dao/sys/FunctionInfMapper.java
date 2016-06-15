package com.centerm.dao.sys;

import java.util.List;

import com.centerm.base.BaseMapper;
import com.centerm.model.sys.FunctionInf;
import com.centerm.model.sys.RoleInf;

public interface FunctionInfMapper extends BaseMapper<FunctionInf>{
	List<FunctionInf> selectByRole(RoleInf role);
}