package com.pre.zlm.o2o.entity;

import java.util.Date;

import lombok.Data;

/**
 * 顾客消费商品映射
 */
@Data
public class UserGoodsMap {
	
	//主键id
	private Long userGoodsId;
	
	//消费时间
	private Date createTime;
	
	//顾客消费所获得积分
	private Integer point;
	
	//顾客信息实体类
	private User user;
	
	//商品信息实体类
	private Goods goods;
	
	//店铺信息实体类
	private Shop shop;
	
	//操作员的信息实体类
	private User operator;
}
