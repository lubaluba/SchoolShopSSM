package com.pre.zlm.o2o.entity;

import java.util.Date;

import lombok.Data;
@Data
public class ShopCategory {
	/**
	 * 店铺类别id
	 */
	private Long shopCategoryId;
	
	/**
	 * 店铺类别名称
	 */
	private String shopCategoryName;
	
	/**
	 * 类别描述
	 */
	private String shopCategoryDesc;
	
	/**
	 * 缩略图
	 */
	private String shopCategoryImg;
	
	/**
	 * 权重
	 */
	private Integer priority;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后编辑时间
	 */
	private Date lastEditTime;
	
	/**
	 * 父类别
	 * 这里是层次关系,如果为null,说明是父类。如果不为空则说明是某一类别子类,比如奶茶店属于饮品类
	 */
	private ShopCategory parent;

	public ShopCategory() {
	}

	public ShopCategory(Long shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	
	
}
