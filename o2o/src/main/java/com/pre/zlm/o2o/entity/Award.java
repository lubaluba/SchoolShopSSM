package com.pre.zlm.o2o.entity;

import java.util.Date;

import lombok.Data;

/**
 * 奖品信息
 */
@Data
public class Award {

	/**
	 * 主键
	 */
	private Long awardId;
	
	/**
	 * 奖品名
	 */
	private String awardName;
	
	/**
	 * 奖品描述
	 */
	private String awardDesc;

	/**
	 * 奖品图片地址
	 */
	private String awardImg;
	
	/**
	 * 需要多少积分兑换
	 */
	private Integer point;
	
	/**
	 * 优先值
	 */
	private Integer priority;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后修改时间
	 */
	private Date LastEditTime;
	
	/**
	 * 状态0不可用,1可用
	 */
	private Integer enableStatus;
	
	/**
	 * 属于哪个店铺
	 */
	private Long shopId;
	
}

