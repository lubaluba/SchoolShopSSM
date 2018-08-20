package com.pre.zlm.o2o.web.shopController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pre.zlm.o2o.entity.GoodsCategory;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.service.GoodsCategoryService;

/**
 * 店铺商品管理
 */
@Controller
@RequestMapping(value = "shopAdmin")
public class ShopGoodsManagementController {
	
	@Autowired
	private GoodsCategoryService goodsService;
	/**
	 * 根据id获取商铺中商品类别信息
	 */
	@RequestMapping(value = "/getgoodscategory", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopCategoryListByShopId(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		
		//从session获取需要查询的店铺id
		Shop currentShop  = (Shop)request.getSession().getAttribute("currentShop");
		
		List<GoodsCategory> goodsCategoryList = null;
		if (currentShop != null && currentShop.getShopId() > 0) {
			goodsCategoryList  = goodsService.listShopCategory(currentShop.getShopId());
			result.put("success", true);
			result.put("goodscategorylist", goodsCategoryList);
		} else {
			result.put("success", false);
			result.put("errMsg", "查询店铺类别信息失败");
		}
		return result;
	}
	
}
