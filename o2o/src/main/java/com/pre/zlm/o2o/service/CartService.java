package com.pre.zlm.o2o.service;

import java.util.List;

import com.pre.zlm.o2o.dto.Cart;
import com.pre.zlm.o2o.dto.Order;
import com.pre.zlm.o2o.entity.CartItem;
import com.pre.zlm.o2o.entity.ExpenseRecord;

public interface CartService {
	
	/**
	 *	获取用户的购物车
	 */
	public Cart getUserCart(Long userId);
	
	/**
	 * 	商品添加到购物车
	 */
	public boolean addGoodsToCart(Long userId, int number, double oneprice, Long goodsId);
	
	/**
	 * 	结算(将购物车清空并存入消费记录)
	 */
	public boolean purchase(Long userId);

	/**
	 * 	检查某件商品是否在用户的购物车内
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	public CartItem CheckCartItemByGoodsIdAndUserId(Long userId, Long goodsId);
	
	/**
	 * 获取用户的消费记录
	 */
	public List<Order> getCustomerOrders(Long userId);
	
	/**
	 * 获取店铺的消费记录
	 */
	public List<ExpenseRecord> getShopOrders(Long shopId);
}
