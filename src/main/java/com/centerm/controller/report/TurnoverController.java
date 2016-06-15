package com.centerm.controller.report;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.centerm.model.report.TurnoverInf;
import com.centerm.service.report.ITurnoverServiceImpl;
import com.centerm.utils.StringUtils;

@Controller
@RequestMapping("report/turnover")
public class TurnoverController {

	private ITurnoverServiceImpl turnoverServiceImpl;

	public ITurnoverServiceImpl getTurnoverServiceImpl() {
		return turnoverServiceImpl;
	}
	@Autowired
	public void setTurnoverServiceImpl(ITurnoverServiceImpl turnoverServiceImpl) {
		this.turnoverServiceImpl = turnoverServiceImpl;
	}

	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("turnoverInf") TurnoverInf turnover) throws Exception {
		if (!StringUtils.isNull(turnover.getStartDate())) {
			turnover.setStartDate(turnover.getStartDate().replace("-", ""));
		}
		if (!StringUtils.isNull(turnover.getEndDate())) {
			turnover.setEndDate(turnover.getEndDate().replace("-", ""));
		}
		return turnoverServiceImpl.list(turnover, null);
	}
}
