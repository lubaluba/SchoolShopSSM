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
import com.pre.zlm.o2o.utils.HttpServletRequestUtils;
@Controller
@RequestMapping("/shopAdmin")
public class ShopController {
	
	@Autowired
	private ShopService service;
	
	@Autowired
	private ShopCategoryService shopCategoryService;
		
	
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
	@RequestMapping(value="/registershop",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerShop(HttpServletRequest request) {
		
		Map<String,Object> modelMap =new HashMap<>();
		
		//接受并转化相应的参数,包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtils.getString(request, "shopstr");
		
		//jackson的用法,将json转换为POJOS,将参数转换为实体类
		ObjectMapper mapper =new ObjectMapper();
		Shop shop =null;
		try {
			shop=mapper.readValue(shopStr,Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "参数json转换异常");
			return modelMap;
		}
		//图片上传读取转换
		CommonsMultipartFile shopImg=null;
		CommonsMultipartResolver cmpr =new CommonsMultipartResolver(request.getSession().getServletContext());
		if(cmpr.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest)request;
			shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success",false);
			modelMap.put("errMsg","上传图片不可为空");
			return modelMap;
		}
		
		//注册店铺
		if (shop != null && shopImg != null) {
		//注册店铺时需要店主的信息,此时可以通过session获取。
			UserInfo owner = new UserInfo();
			owner.setUserId(1l);
			shop.setOwner(owner);
			ShopExecution se;
			try {
				se = service.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
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
	
