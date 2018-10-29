package com.pre.zlm.o2o.web.shopController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/shopAdmin", method = RequestMethod.GET)
public class ShopAdminController {
	
	@RequestMapping(value = "/toAddShopPage")
	public String shopOperation() {
		return "shop/addShop";
	}
	
	@RequestMapping(value = "/toUpdatePage")
	public String updateShopPage() {
		return "shop/updateshop";
	}
	
	@RequestMapping(value = "/toShopList")
	public String shopList() {
		return "shop/shoplist";
	}
	
	@RequestMapping(value = "/toShopManagement")
	public String shopManagement() {
		return "shop/shopmanagement";
	}
	
	@RequestMapping(value = "toGoodsCategoryManagement")
	public String goodsCategoryManagement() {
		return "shop/goodscategorymanagement";
	}
	
	@RequestMapping(value = "/togoodsmanagment")
	public String toGoodsManagment() {
		return "goods/goodsmanagment";
	}
	@RequestMapping(value = "/togoodsoperation")
	public String toGoodsOperation() {
		return "goods/goodsoperation";
	}
}
