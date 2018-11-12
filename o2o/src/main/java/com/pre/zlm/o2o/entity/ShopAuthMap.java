package com.pre.zlm.o2o.entity;

import java.util.Date;

import lombok.Data;

/**
 * 店铺授权
 */
@Data
public class ShopAuthMap {

	//主键
	private Long ShopAuthId;
	
	//职位名
	private String title;
	
	//职位符号(可用于权限控制)
	private Integer titleFlag;
	
	//员工状态 0无效,1有效
	private Integer enableStatus;
	
	//创建时间
	private Date createTime;
	
	//最近更新时间
	private Date lastEditTime;
	
	//员工信息实体类
	private User employee;
	
	//店铺信息实体类
	private Shop shop;
}
