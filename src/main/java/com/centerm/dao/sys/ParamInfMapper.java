package com.centerm.dao.sys;

import com.centerm.base.BaseMapper;
import com.centerm.model.sys.ParamInf;

public interface ParamInfMapper extends BaseMapper<ParamInf>{
	public ParamInf getParam(ParamInf param);
}