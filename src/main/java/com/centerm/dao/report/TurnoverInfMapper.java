package com.centerm.dao.report;

import java.util.List;
import java.util.Map;

import com.centerm.base.BaseMapper;
import com.centerm.model.report.TurnoverInf;

public interface TurnoverInfMapper extends BaseMapper<TurnoverInf>{
	public List<TurnoverInf> querySummary(Map map);

	public List<TurnoverInf> queryByDay(Map<String, Object> map);
	
	public List<TurnoverInf> queryByMonth(Map<String, Object> map);
}