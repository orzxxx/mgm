package com.centerm.controller.prom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.centerm.model.prom.DiscountRuleInf;
import com.centerm.model.prom.DiscountTimeInf;
import com.centerm.model.sys.ParamInf;
import com.centerm.service.prom.IDiscountConfigService;

@Controller
@RequestMapping("prom/discount")
public class DiscountConfigController {
	@Autowired
	private IDiscountConfigService discountConfigServiceImpl;

	@RequestMapping("/get")
	@ResponseBody()
	public Object get(String mchntCd) throws Exception {
		return discountConfigServiceImpl.getModeParamByMchntCd(mchntCd);
	}
	
	@RequestMapping("/del")
	@ResponseBody()
	public Object del(@ModelAttribute("discountTimeInf") DiscountTimeInf discountTime) throws Exception {
		discountConfigServiceImpl.del(discountTime);
		return null;
	}
	
	@RequestMapping("/save")
	@ResponseBody()
	public Object save(@ModelAttribute("paramInf") ParamInf param, @RequestParam("discountTimeJson")String discountTimeJson) throws Exception {
		//只处理折扣一二
		discountConfigServiceImpl.save(param, JSON.parseArray(discountTimeJson, DiscountTimeInf.class));
		return null;
	}
	
	@RequestMapping("/add")
	@ResponseBody()
	public Object add(@ModelAttribute("discountTimeInf") DiscountTimeInf discountTime, @RequestParam("discountRuleJson")String discountRuleJson) throws Exception {
		//只处理折扣三
		discountConfigServiceImpl.add(discountTime, JSON.parseArray(discountRuleJson, DiscountRuleInf.class));
		return null;
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public Object update(@ModelAttribute("discountTimeInf") DiscountTimeInf discountTime, @RequestParam("discountRuleJson")String discountRuleJson) throws Exception {
		discountConfigServiceImpl.update(discountTime, JSON.parseArray(discountRuleJson, DiscountRuleInf.class));
		return null;
	}
	
	@RequestMapping("/list")
	@ResponseBody()
	public Object list(String mchntCd) throws Exception {
		return discountConfigServiceImpl.list(mchntCd, null);
	}
}
