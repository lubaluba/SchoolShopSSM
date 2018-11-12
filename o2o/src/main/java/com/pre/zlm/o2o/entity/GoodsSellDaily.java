package com.pre.zlm.o2o.entity;

import java.util.Date;

import lombok.Data;

/**
 * 顾客消费商品实体类
 */
@Data
public class GoodsSellDaily {
	
	//哪天的销量,精确到天
	private Date createTime;
	
	//销量
	private Integer total;
	
	//商品信息实体类
	private Goods goods;
	
	//店铺信息实体类
	private Shop shop;
}
