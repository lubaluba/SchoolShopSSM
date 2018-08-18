package com.pre.zlm.o2o.web.shopController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pre.zlm.o2o.dto.ShopExecution;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.entity.ShopCategory;
import com.pre.zlm.o2o.entity.UserInfo;
import com.pre.zlm.o2o.enums.ShopStateEnum;
import com.pre.zlm.o2o.exception.ShopOperationException;
import com.pre.zlm.o2o.service.ShopCategoryService;
import com.pre.zlm.o2o.service.ShopService;
import com.pre.zlm.o2o.utils.CodeUtil;
import com.pre.zlm.o2o.utils.HttpServletRequestUtils;
@Controller
@RequestMapping("/shopAdmin")
public class ShopController {
	
	@Autowired
	private ShopService service;
	
	@Autowired
	private ShopCategoryService shopCategoryService;

	/**
	 * 检查当前用户是否有权限操作shop,主要看是否登录
	 */
	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		UserInfo user = new UserInfo();
		user.setUserId(11L);
		user.setName("王小二");
		request.getSession().setAttribute("user", user);
		user = (UserInfo)request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = service.listShopByCondition(shopCondition, 0, 1);
			result.put("success", true);
			result.put("shoplist", se.getShopList());
			result.put("user", user);
		} catch (Exception e) {
			result.put("success", false);
			result.put("errMsg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 点击进入店铺展示详情页面
	 */
	@RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		long shopId = HttpServletRequestUtils.getLong(request, "shopId");
		if (shopId <= 0) {
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if (currentShopObj == null) {
				result.put("redirect", true);
				result.put("url", "o2o/shop/shoplist");
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
	 * 管理员根据条件查询店铺信息
	 */
	@RequestMapping(value = "queryShopList", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object>  queryShop(){
		Map<String, Object> result = new HashMap<>();
		return result;
	}
	
	/**
	 *	更新店铺
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
	
	@RequestMapping(value = "/updateshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> updateShop(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		//检验验证码
		if (! CodeUtil.checkVerifyCode(request)) {
			result.put("success", false);
			result.put("errMsg", "验证码错误");
			return result;
		}
		
		//参数转换为实体
		String shopStr = HttpServletRequestUtils.getString(request, "shopstr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop=mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			result.put("success", false);
			result.put("errMsg", "参数json转换异常");
			return result;
		}
		
		//处理上传图片
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver cmpr = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (cmpr.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}
		
		//更新店铺
		if (shop != null && shop.getShopId() != null) {
			UserInfo owner = (UserInfo)request.getSession().getAttribute("user");
			shop.setOwner(owner);
			ShopExecution se;
			try {
				if (shopImg == null) {
					se = service.updateShop(shop, null, null);
				} else {
					se = service.updateShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				}
				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
					result.put("success", true);
				} else {
					result.put("success", false);
					result.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException | IOException e ) {
				result.put("success", false);
				result.put("errMsg", e.getMessage());
				return result;
			}	
		} else {
			result.put("success", false);
			result.put("errMsg", "请输入店铺id");
			return result;
		}
		return result;
	}
	
	@RequestMapping(value = "/getshopCategorylist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopCategoryList(HttpServletRequest request) {
		
		Map<String, Object> result =new HashMap<>();
		List<ShopCategory> shopCategoryList  = new ArrayList<>();
		
		try {
			shopCategoryList = shopCategoryService.listShopCategory(new ShopCategory());
			result.put("success", true);
			result.put("rows", shopCategoryList);
			result.put("total", shopCategoryList.size());
		} catch(Exception e) {
			result.put("success", false);
			result.put("errMsg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 注册店铺
	 */
	@RequestMapping(value="/registershop", method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerShop(HttpServletRequest request) {
		Map<String,Object> modelMap = new HashMap<>();
		if (! CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		//接受并转化相应的参数,包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtils.getString(request, "shopstr");
		//jackson的用法,将json转换为POJOS,将参数转换为实体类
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop=mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "参数json转换异常");
			return modelMap;
		}
		//图片上传读取转换
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver cmpr = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (cmpr.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg"," 上传图片不可为空");
			return modelMap;
		}
		
		//注册店铺
		if (shop != null && shopImg != null) {
		//注册店铺时需要店主的信息,此时可以通过session获取。
			UserInfo owner = (UserInfo)request.getSession().getAttribute("user");
			shop.setOwner(owner);
			ShopExecution se;
			try {
				se = service.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					if (shopList == null) {
						shopList = new ArrayList<>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException | IOException e ) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}	
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
	}
	
	
}
	
