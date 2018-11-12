package com.pre.zlm.o2o.entity;

import java.util.Date;

import lombok.Data;

/**
 * 顾客店铺积分映射
 */
@Data
public class UserShopMap {
	
	//主键id
	private Long userShopId;
	
	//创建时间
	private Date createTime;
	
	//顾客在该店积分
	private Integer point;
	
	//顾客信息实体类
	private User user;
	
	//店铺信息实体类
	private Shop shop;

}

