package com.pre.zlm.o2o.web.shopController;

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

import com.pre.zlm.o2o.dto.ImageHolder;
import com.pre.zlm.o2o.dto.ShopExecution;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.entity.User;
import com.pre.zlm.o2o.enums.ShopStateEnum;
import com.pre.zlm.o2o.exception.ShopOperationException;
import com.pre.zlm.o2o.service.ShopService;
import com.pre.zlm.o2o.web.BaseController;

@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController extends BaseController {

	@Autowired
	private ShopService service;

	// 店铺管理员获取店铺列表,根据user获取店铺列表
	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		//这里从session中获取user
		User user = (User) request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = service.listShopByCondition(shopCondition, 0, 10);
			result.put("success", true);
			result.put("shoplist", se.getShopList());
			result.put("user", user);
			return result;
		} catch (Exception e) {
			return exceptionResult(result, e.getMessage());
		}
	}

	/**
	 * 注册店铺
	 */
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		if (!checkCode()) {
			return exceptionResult(result, "验证码错误");
		}
		Shop shop = null;
		try {
			shop = (Shop) getObject("shopstr", Shop.class);
		} catch (Exception e) {
			return exceptionResult(result, "参数转换异常");
		}
		// 图片上传读取转换
		ImageHolder shopImg = null;
		try {
			shopImg = ResolverImg("shopImg");
		} catch (Exception e) {
			return exceptionResult(result, "上传图片不可为空");
		}
		if (shop == null || shopImg == null) {
			return exceptionResult(result, "请输入店铺信息");
		}
		// 注册店铺
		User owner = (User)request.getSession().getAttribute("user");
		shop.setOwner(owner);
		try {
			ShopExecution se = service.addShop(shop, shopImg);
			if (se.getState() == ShopStateEnum.CHECK.getState()) {
				result.put("success", true);
				@SuppressWarnings("unchecked")
				List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
				if (shopList == null) {
					shopList = new ArrayList<>();
				}
				shopList.add(se.getShop());
				request.getSession().setAttribute("shopList", shopList);
				return result;
			} else {
				return exceptionResult(result, se.getStateInfo());
			}
		} catch (ShopOperationException e) {
			logger.error("店铺插入失败");
			return exceptionResult(result, e.toString());
		}
	}
	
	/**
	 *	更新店铺
	 */
	@RequestMapping(value = "/updateshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> updateShop(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		//检验验证码
		if (! checkCode()) {
			return exceptionResult(result, "验证码错误");
		}
		//参数转换为实体
		Shop shop = null;
		try {
			shop = (Shop)getObject("shopstr", Shop.class);
		} catch (Exception e) {
			return exceptionResult(result, "参数转换异常");
		}
		//图片上传读取转换
		ImageHolder shopImg = null;
		try {
			shopImg = ResolverImgForUpdate("shopImg");
		} catch (Exception e) {
			return exceptionResult(result, "图片处理失败");
		}

		if(shop == null || shop.getShopId() == null) {
			return exceptionResult(result, "请输入店铺id");
		}

		shop.setOwner((User)request.getSession().getAttribute("user"));
		try {
			ShopExecution se = service.updateShop(shop, shopImg);
			if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
				result.put("success", true);
				return result;
			} else {
				return exceptionResult(result, se.getStateInfo());
			}
		} catch (ShopOperationException e ) {
			return exceptionResult(result, e.toString());
		}	
	}
}
