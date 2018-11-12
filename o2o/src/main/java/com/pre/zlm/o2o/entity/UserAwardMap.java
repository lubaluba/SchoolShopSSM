package com.pre.zlm.o2o.entity;

import java.util.Date;

import lombok.Data;

/**
 * 顾客已领取的奖品映射
 */
@Data
public class UserAwardMap {
	
	/**
	 * 主键id
	 */
	private Long userAwardId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 使用状态0未兑换 1已兑换
	 */
	private Integer usedStatus;
	
	/**
	 * 顾客信息实体类
	 */
	private User user;
	
	/**
	 * 奖品信息实体类
	 */
	private Award award;
	
	/**
	 * 店铺信息实体类
	 */
	private Shop shop;
	
	/**
	 * 操作员实体类
	 */
	private User operator;
	
}
