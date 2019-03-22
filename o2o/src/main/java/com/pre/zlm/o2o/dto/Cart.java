package com.pre.zlm.o2o.dto;

import java.util.List;

import com.pre.zlm.o2o.entity.CartItem;
import com.pre.zlm.o2o.entity.User;

import lombok.Data;

@Data
public class Cart {
	
	/**
	 *	购物车所属用户
	 */
	private User user;
	
	
	/**
	 * 	购物车的商品
	 */
	private List<CartItem> goodsList;
	
	/**
	 * 	商品总价
	 */
	private Double totalPrice;
	
	/**
	 * 	总商品数目
	 */
	private Integer total;
}


