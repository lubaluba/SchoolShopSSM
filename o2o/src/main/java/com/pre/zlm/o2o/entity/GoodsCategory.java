package com.pre.zlm.o2o.entity;

import java.util.Date;

import lombok.Data;
@Data
public class GoodsCategory {
	/**
	 * 商品类别ID
	 */
	private Long goodsCategoryId;
	
	/**
	 * 所属店铺ID
	 */
	private Long shopId;
	
	/**
	 * 商品类别名称
	 */
	private String goodsCategoryName;
	
	/**
	 * 权重
	 */
	private Integer priority;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
}
