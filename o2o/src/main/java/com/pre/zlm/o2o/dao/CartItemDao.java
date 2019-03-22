package com.pre.zlm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pre.zlm.o2o.entity.CartItem;

public interface CartItemDao {
	/**
	 *	获取某用户购物车内容
	 */
	List<CartItem> getCartByUser(long userId);
	
	/**
	 * 	将商品添加到购物车
	 */
	int addGoodsIntoCart(CartItem cartItem);
	
	/**
	 * 	购买(将商品状态设置为1)
	 */
	int updateStatus(@Param("status")int status, @Param("userId")Long userId);

	/**
	 * 根据goodsId和userId查询
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	CartItem getCartItemByUserIdAndGoodsId(@Param("userId")Long userId, @Param("goodsId")Long goodsId, @Param("status")int status);
}
