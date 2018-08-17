package com.pre.zlm.o2o.web.shopController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/shopAdmin",method=RequestMethod.GET)
public class ShopAdminController {
	
	@RequestMapping(value="/toAddShopPage")
	public String shopOperation() {
		return "shop/addShop";
	}

}
