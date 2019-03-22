package com.pre.zlm.o2o.entity;

import lombok.Data;

@Data
public class CartItem {
	/**
	 *	订单编号
	 */
	private String itemId;
	
	/**
	 * 	商品
	 */
	private Goods goods;
	
	/**
	 * 店铺
	 */
	private Shop shop;
	
	/**
	 * 	购买数量
	 */
	private Integer number;
	
	/**
	 *	总价
	 */
	private Double price;
	
	/**
	 * 	用户id
	 */
	private Long userId;
	
	/**
	 *	状态
	 */
	private int status;
}
