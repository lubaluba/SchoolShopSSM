package com.pre.zlm.o2o.entity;

import java.util.Date;

import lombok.Data;
@Data
public class ShopCategory {
	
	private Long shopCategoryId;
	
	private String shopCategoryName;
	
	private String shopCategoryDesc;
	
	private String shopCategoryImg;
	
	private Integer priority;
	
	private Date createTime;
	
	private Date lastEditTime;
	
	/**
	 * 这里是层次关系,如果为null,说明是父类。如果不为空则说明是某一类别子类,比如奶茶店属于饮品类
	 */
	private ShopCategory parent;
}
