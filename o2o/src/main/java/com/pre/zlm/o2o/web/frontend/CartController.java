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

import com.pre.zlm.o2o.dto.Cart;
import com.pre.zlm.o2o.dto.Order;
import com.pre.zlm.o2o.entity.CartItem;
import com.pre.zlm.o2o.entity.ExpenseRecord;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.entity.User;
import com.pre.zlm.o2o.service.CartService;
import com.pre.zlm.o2o.utils.HttpServletRequestUtils;
import com.pre.zlm.o2o.web.BaseController;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCart(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		Cart cart = cartService.getUserCart(user.getUserId());
		result.put("success", true);
		result.put("totalprice", cart.getTotalPrice());
		result.put("items", cart.getGoodsList());
		result.put("total", cart.getTotal());
		return result;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addToCart(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		int number = HttpServletRequestUtils.getInt(request, "number");
		double onePrice = HttpServletRequestUtils.getDouble(request, "price");
		User user = (User)request.getSession().getAttribute("user");
		Long goodsId = HttpServletRequestUtils.getLong(request, "goodsId");
		if(user == null || user.getUserId() == null) {
			return exceptionResult(result, "用户未登录,请先登录");
		}
		boolean success = cartService.addGoodsToCart(user.getUserId(), number, onePrice, goodsId);
		if (success) {
			result.put("success", true);
		} else {
			return exceptionResult(result, "添加至购物车失败");
		}
		return result;
	}
	
	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> buy(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = (User)request.getSession().getAttribute("user");
		if(user == null || user.getUserId() == null) {
			return exceptionResult(result, "用户未登录,请先登录");
		}
		boolean res = cartService.purchase(user.getUserId());
		if (! res) {
			return exceptionResult(result, "购买失败！！");
		}
		result.put("success", true);
		return result;
	}
	
	/**
	 * 	检查某件商品是否已经在购物车里
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkCart(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = (User)request.getSession().getAttribute("user");
		if(user == null || user.getUserId() == null) {
			result.put("success", true);
			result.put("isExist", false);
			return result;
		}
		Long goodsId = HttpServletRequestUtils.getLong(request, "goodsId");
		CartItem item = cartService.CheckCartItemByGoodsIdAndUserId(user.getUserId(), goodsId);
		if (item == null) {
			result.put("isExist", false);
		} else {
			result.put("isExist", true);
		}
		result.put("success", true);
		return result;
	}
	
	
	@RequestMapping(value = "/getRecord", method = RequestMethod.GET)  
	@ResponseBody 
	public Map<String, Object> getRecord(HttpServletRequest request){ 
		Map<String, Object> result = new HashMap<>();
		User user = (User)request.getSession().getAttribute("user");
		List<Order> orderList = cartService.getCustomerOrders(user.getUserId());
		result.put("success", true);
		result.put("list", orderList);
		return result;
	}
	 
	@RequestMapping(value = "/getShopRecord", method = RequestMethod.GET)  
	@ResponseBody 
	public Map<String, Object> getShopRecord(HttpServletRequest request){ 
		Shop currentShop  = (Shop)request.getSession().getAttribute("currentShop");
		Map<String, Object> result = new HashMap<>();
		List<ExpenseRecord> orderList = cartService.getShopOrders(currentShop.getShopId());
		result.put("success", true);
		result.put("list", orderList);
		return result;
	}
	 
	
}
