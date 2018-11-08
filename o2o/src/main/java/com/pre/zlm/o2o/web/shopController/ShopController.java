 package com.pre.zlm.o2o.web.shopController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pre.zlm.o2o.dto.ShopExecution;
import com.pre.zlm.o2o.entity.Area;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.entity.ShopCategory;
import com.pre.zlm.o2o.service.AreaService;
import com.pre.zlm.o2o.service.ShopCategoryService;
import com.pre.zlm.o2o.service.ShopService;
import com.pre.zlm.o2o.utils.HttpServletRequestUtils;
import com.pre.zlm.o2o.web.BaseController;
@Controller
@RequestMapping("/shop")
public class ShopController extends BaseController {
	
	@Autowired
	private ShopService service;
	
	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@Autowired
	private AreaService areaService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	//获取店铺列表信息
	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShop(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		int pageSize = HttpServletRequestUtils.getInt(request, "pageSize");
		int pageIndex = HttpServletRequestUtils.getInt(request, "pageIndex");
		if (pageSize > -1 && pageIndex > -1) {
			long parentId = HttpServletRequestUtils.getLong(request, "parentId");
			long shopCategoryId = HttpServletRequestUtils.getLong(request, "shopCategoryId");
			int areaId = HttpServletRequestUtils.getInt(request, "areaId");
			String shopName = HttpServletRequestUtils.getString(request, "shopName");
			Shop shopCondition = compactShopCondition(parentId, shopCategoryId, areaId, shopName);
			ShopExecution se = service.listShopByCondition(shopCondition, pageIndex, pageSize);
			result.put("shopList", se.getShopList());
			result.put("count", se.getCount());
			result.put("success", true);
		} else {
			logger.error("店铺信息查询失败");
			exceptionResult(result, "查询失败");
		}
		return result;
	}
	

	/**
	 * 封装条件查询店铺的条件
	 */
	private Shop compactShopCondition(long parentId, long shopCategoryId, int areaId, String shopName) {
		Shop shopCondition = new Shop();
		if (parentId > 0L) {
			ShopCategory sc = new ShopCategory();
			sc.setParent(new ShopCategory(parentId));
			shopCondition.setShopCategory(sc);
		}
		if (shopCategoryId > 0L) {
			ShopCategory sc = new ShopCategory();
			sc.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(sc);
		}
		if (areaId > 0) {
			Area area = new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}
		if (shopName != null) {
			shopCondition.setShopName(shopName);
		}
		return shopCondition;
	}

	
	//获取店铺查询条件(类别和区域信息)
	@RequestMapping(value = "/shopquerycondition", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopsPageInfo(HttpServletRequest request){
		Map<String, Object> result = new HashMap<>();
		Long parentId = HttpServletRequestUtils.getLong(request, "parentId");
		//如果parentId存在,就取出该以及类别下的全部二级类别,如果不存在就取出全部的一级类别
		List<ShopCategory> shopCategoryList = null;
		try {
			if (parentId > 0) {
				ShopCategory shopCategoryCondition = new ShopCategory();
				shopCategoryCondition.setParent(new ShopCategory(parentId));
				shopCategoryList = shopCategoryService.listShopCategory(shopCategoryCondition);	
			} else {
				shopCategoryList = shopCategoryService.listShopCategory(null);
			}
		} catch (Exception e) {
			exceptionResult(result, e.getMessage());
		}
		result.put("success", true);
		result.put("shopCategoryList", shopCategoryList);
		try {
			List<Area> areaList = areaService.getAreaList();
			result.put("areaList", areaList);
		} catch (Exception e) {
			exceptionResult(result, e.getMessage());
		}
		return result;
	}
	
	//获取店铺详情
	@RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		long shopId = HttpServletRequestUtils.getLong(request, "shopId");
		if (shopId <= 0) {
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if (currentShopObj == null) {
				result.put("redirect", true);
				result.put("url", "/o2o/shopAdmin/toShopList");
			} else {
				Shop currentShop = (Shop)currentShopObj;
				result.put("redirect", false);
				result.put("shopId", currentShop.getShopId());
			}
		} else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			result.put("redirect", false);
			result.put("shopId", currentShop.getShopId());
		}
		return result;
	}
		
	/**
	 *	获取店铺类别列表
	 */
	@RequestMapping(value = "/getshopCategorylist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopCategoryList(HttpServletRequest request) {
		Map<String, Object> result =new HashMap<>();
		List<ShopCategory> shopCategoryList;
		try {
			long id = HttpServletRequestUtils.getLong(request, "parentId");
			if(id < 0) {
				shopCategoryList = shopCategoryService.listShopCategory(null);
			}else {
				ShopCategory shopCategoryCondition = new ShopCategory();
				ShopCategory parent = new ShopCategory();
				parent.setShopCategoryId(id);
				shopCategoryCondition.setParent(parent);
				shopCategoryList = shopCategoryService.listShopCategory(shopCategoryCondition);
			}
			result.put("success", true);
			result.put("rows", shopCategoryList);
			result.put("total", shopCategoryList.size());
			return result;
		} catch(Exception e) {
			return exceptionResult(result, e.toString());
		}
	}
	
	
	
	/**
	 *	根据id获取店铺详情
	 */
	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request){
		Map<String, Object> result = new HashMap<>();
		Long shopId = HttpServletRequestUtils.getLong(request, "shopId");
		if (shopId > -1) {
			try {
				Shop shop = service.getShopById(shopId);
				result.put("shop", shop);
				result.put("success", true);
			} catch (Exception e) {
				result.put("success", false);
				result.put("errMsg", e.toString());
			}
		} else {
			result.put("success", false);
			result.put("errMsg", "empty shopId");
		}
		return result;
	}
	
}