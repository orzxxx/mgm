package com.centerm.controller.prom;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSON;
import com.centerm.base.Page;
import com.centerm.model.menu.ChildComboTypeInf;
import com.centerm.model.menu.MenuTypeInf;
import com.centerm.model.prom.DiscountTimeInf;
import com.centerm.model.sys.ParamInf;
import com.centerm.service.prom.IDiscountConfigServiceImpl;

@Controller
@RequestMapping("prom/discount")
public class DiscountConfigController {
	@Autowired
	private IDiscountConfigServiceImpl discountConfigServiceImpl;

	@RequestMapping("/get")
	@ResponseBody()
	public Object get(String mchntCd) throws Exception {
		return discountConfigServiceImpl.getModeParamByMchntCd(mchntCd);
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(int id) throws Exception {
		discountConfigServiceImpl.del(id);
		return null;
	}
	
	@RequestMapping("/add")
	@ResponseBody()
	public Object add(@ModelAttribute("paramInf") ParamInf param, @RequestParam("discountTimeJson")String discountTimeJson) throws Exception {
		discountConfigServiceImpl.add(param, JSON.parseArray(discountTimeJson, DiscountTimeInf.class));
		return null;
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("DiscountTimeInf") DiscountTimeInf discountTime) throws Exception {
		discountConfigServiceImpl.update(discountTime);
		return null;
	}
}
