 package com.pre.zlm.o2o.web.shopController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pre.zlm.o2o.dto.GoodsExecution;
import com.pre.zlm.o2o.dto.ImageHolder;
import com.pre.zlm.o2o.entity.Goods;
import com.pre.zlm.o2o.entity.GoodsCategory;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.enums.GoodsStateEnum;
import com.pre.zlm.o2o.exception.GoodsOperationException;
import com.pre.zlm.o2o.service.GoodsCategoryService;
import com.pre.zlm.o2o.service.GoodsService;
import com.pre.zlm.o2o.utils.HttpServletRequestUtils;
import com.pre.zlm.o2o.web.BaseController;

@Controller
@RequestMapping("/shopAdmin")
public class GoodsController extends BaseController {
	
	@Autowired
	private GoodsService service;
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	//支持商品详情图上传最大数量
	private static final int IMAGEMAXCOUNT = 6;
	
	@RequestMapping(value = "/addgoods", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addGood(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		if (!checkCode()) {
			return exceptionResult(result, "验证码错误");
		}
		List<ImageHolder> goodsImgList;	//商品详情图片
		ImageHolder thumbnail = null;	//商品缩略图
		try {
			thumbnail = ResolverImg("thumbnail");
			goodsImgList = ResolverImgList("goodsImg", IMAGEMAXCOUNT);
		} catch (IOException e1) {
			return exceptionResult(result, "处理商品详情图失败");
		}
		
		Goods goods;
		try {
			goods = (Goods) getObject("goodsStr", Goods.class);
		} catch (Exception e1) {
			return exceptionResult(result, "json参数转换异常");
		}
		
		//插入商品信息;
		if(goods == null || thumbnail == null || goodsImgList.size() <= 0) {
			return exceptionResult(result, "请输入商品信息");
		}
		try {
			Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
			goods.setShop(currentShop);
			GoodsExecution pe = service.addGoods(goods, thumbnail, goodsImgList);
			if (pe.getState() == GoodsStateEnum.SUCCESS.getState()) {
				result.put("success", true);
				return result;
			} else {
				return exceptionResult(result, pe.getStateInfo());
			} 
		} catch (GoodsOperationException e) {
			return exceptionResult(result, e.toString());
		}		
	}
	
	@RequestMapping(value = "/getgoodsbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getGoodsById(@RequestParam Long goodsId) {
		Map<String, Object> result = new HashMap<>();
		if (goodsId > -1) {
			Goods goods = service.getGoodsById(goodsId);
			List<GoodsCategory> goodsCategoryList = goodsCategoryService.listShopCategory(goods.getShop().getShopId());
			result.put("goods", goods);
			result.put("goodsCategoryList", goodsCategoryList);
			result.put("success", true);
		} else {
			exceptionResult(result, "商品id为空");
		}
		return result;
	}
	
	@RequestMapping(value = "/modifygoods", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyGoods(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		if (! checkCode()) {
			return exceptionResult(result, "验证码错误！");
		}
		List<ImageHolder> goodsImgList = null;	
		ImageHolder thumbnail = null;
		try {
			thumbnail = ResolverImg("thumbnail");
			goodsImgList = ResolverImgList("goodsImg", IMAGEMAXCOUNT);
		} catch (IOException e1) {
			return exceptionResult(result, "处理商品详情图失败");
		}	
		Goods goods = null;
		try {
			goods = (Goods) getObject("goodsStr", Goods.class);
		} catch (Exception e1) {
			return exceptionResult(result, "json参数转换异常");
		}
		if (goods != null) {
			try {
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				goods.setShop(currentShop);
				GoodsExecution ge = service.modifyGoods(goods, thumbnail, goodsImgList);
				if (ge.getState() == GoodsStateEnum.SUCCESS.getState()) {
					result.put("success", true);
				} else {
					exceptionResult(result, ge.getStateInfo());
				}
			} catch (RuntimeException e) {
				exceptionResult(result, e.getMessage());
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/getGoodsList", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getGoodsListPages(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		int pageIndex = HttpServletRequestUtils.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtils.getInt(request, "pageSize");
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		if (pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
			long goodsCategoryId = HttpServletRequestUtils.getInt(request, "goodsCategoryId");
			String goodsName = HttpServletRequestUtils.getString(request, "goodsName");
			int enableStatus = HttpServletRequestUtils.getInt(request, "enableStatus");
			Goods goodsCondition = compactGoodsCondition(currentShop.getShopId(), goodsCategoryId, goodsName, enableStatus);
			GoodsExecution ge = service.getGoodsList(goodsCondition, pageIndex, pageSize);
			result.put("success", true);
			result.put("goodsList", ge.getGoodsList());
			result.put("count", ge.getCount());
		} else {
			exceptionResult(result, "查询条件不正确");
		}
		return result;
	}
	
	private Goods compactGoodsCondition(Long shopId, Long goodsCategoryId, String goodsName, Integer enableStatus) {
		Goods goodsCondition = new Goods();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		goodsCondition.setShop(shop);
		if (goodsCategoryId > 0L) {
			GoodsCategory gc = new GoodsCategory();
			gc.setGoodsCategoryId(goodsCategoryId);
			goodsCondition.setGoodsCategory(gc);
		}
		if (goodsName != null) {
			goodsCondition.setGoodsName(goodsName);
		}
		if (enableStatus == 0 || enableStatus == 1) {
			goodsCondition.setEnableStatus(enableStatus);
		}
		return goodsCondition;
	}
}
	
