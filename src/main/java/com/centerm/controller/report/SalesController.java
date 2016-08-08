package com.centerm.controller.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.centerm.base.Page;
import com.centerm.model.report.SalesInf;
import com.centerm.service.report.ISalesService;
import com.centerm.utils.StringUtils;

@Controller
@RequestMapping("report/sales")
public class SalesController {

	private ISalesService salesServiceImpl;

	public ISalesService getSalesServiceImpl() {
		return salesServiceImpl;
	}
	@Autowired
	public void setSalesServiceImpl(ISalesService salesServiceImpl) {
		this.salesServiceImpl = salesServiceImpl;
	}

	@RequestMapping("/list")
	@ResponseBody()
	public Object list(@ModelAttribute("salesInf") SalesInf sales, 
			@RequestParam int currentPage, 
			@RequestParam int pageSize) throws Exception {
		if (!StringUtils.isNull(sales.getStartDate())) {
			sales.setStartDate(sales.getStartDate().replace("-", ""));
		}
		if (!StringUtils.isNull(sales.getEndDate())) {
			sales.setEndDate(sales.getEndDate().replace("-", ""));
		}
		Page page = new Page(currentPage, pageSize);
		List<SalesInf> saless = salesServiceImpl.list(sales, page);
		page.setRows(saless);
		return page;
	}
}
