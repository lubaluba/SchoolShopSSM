package com.pre.zlm.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/frontend", method = RequestMethod.GET)
public class FrontendController {
	
	@RequestMapping(value = "/toshoplist", method = RequestMethod.GET)
	public String toShopList() {
		return  "frontend/shoplist";
	}
	
	@RequestMapping(value = "/toshopdetail")
	public String toShopDetail() {
		return "frontend/shopdetail";
	}
	
	@RequestMapping(value ="/index")
	public String toIndex() {
		return "frontend/index";
	}
	
}
