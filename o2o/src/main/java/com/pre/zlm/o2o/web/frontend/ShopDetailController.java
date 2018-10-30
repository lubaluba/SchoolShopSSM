package com.pre.zlm.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pre.zlm.o2o.dto.GoodsExecution;
import com.pre.zlm.o2o.entity.Goods;
import com.pre.zlm.o2o.entity.GoodsCategory;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.service.GoodsCategoryService;
import com.pre.zlm.o2o.service.GoodsService;
import com.pre.zlm.o2o.service.ShopService;
import com.pre.zlm.o2o.utils.HttpServletRequestUtils;
import com.pre.zlm.o2o.web.BaseController;

/**
 * 	商店详情展示
 */

@Controller
@RequestMapping("/frontend")
public class ShopDetailController extends BaseController{
	
	@Autowired
	private ShopService shopService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	/**
	 * 获取店铺信息以及该店铺下面的商品类别列表
	 */
	@RequestMapping(value = "/listshopdetail" , method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		Long shopId = HttpServletRequestUtils.getLong(request, "shopId");
		if (shopId > 0) {
			Shop shop = shopService.getShopById(shopId);
			List<GoodsCategory> goodsCategoryList = goodsCategoryService.listGoodsCategory(shopId);
			result.put("shop", shop);
			result.put("goodsCategoryList", goodsCategoryList);
			result.put("success", true);
		} else {
			exceptionResult(result, "shopId为空");
		}
		return result;
	}
	
	/**
	 * 	根据查询条件分页列出该店铺下所有商品
	 */
	@ResponseBody
	@RequestMapping(value = "/listgoodsbyshop", method = RequestMethod.GET)
	private Map<String, Object> listGoodsByShop(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		int pageIndex = HttpServletRequestUtils.getInt(request, "pageIndex");
		int pageSize  = HttpServletRequestUtils.getInt(request, "pageSize");
		long shopId   = HttpServletRequestUtils.getLong(request, "shopId");
		if (pageIndex > -1 && pageSize > -1 && shopId > -1) {
			long goodsCategoryId = HttpServletRequestUtils.getLong(request, "goodsCategoryId");
			String goodsName = HttpServletRequestUtils.getString(request, "goodsName");
			Goods goodsCondition = compactGoodsCondition4Search(shopId, goodsCategoryId, goodsName);
			GoodsExecution ge = goodsService.getGoodsList(goodsCondition, pageIndex, pageSize);
			result.put("count", ge.getCount());
			result.put("goodsList", ge.getGoods());
			result.put("success", true);
		} else {
			exceptionResult(result, "查询商品信息失败,请检查参数");
		}
		return result;
 	}

	private Goods compactGoodsCondition4Search(long shopId, long goodsCategoryId, String goodsName) {
		Goods goodsCondition = new Goods();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		goodsCondition.setShop(shop);
		if (goodsCategoryId > 0L) {
			goodsCondition.setGoodsCategory(new GoodsCategory(goodsCategoryId));
		}
		if (goodsName != null) {
			goodsCondition.setGoodsName(goodsName);
		}
		goodsCondition.setEnableStatus(1);//只展示状态为上架的商品
		return goodsCondition;
	}
}
