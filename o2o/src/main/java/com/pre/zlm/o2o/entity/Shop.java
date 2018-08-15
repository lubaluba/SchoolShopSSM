package com.pre.zlm.o2o.entity;
import java.util.Date;
import lombok.Data;
@Data
public class Shop {
	/**
	 * 商店id
	 */
	private Long shopId;
	
	/**
	 * 商店名称
	 */
	private String shopName;
	
	/**
	 * 店铺详情
	 */
	private String shopDesc;
	
	/**
	 * 店铺地址
	 */
	private String shopAddr;
	
	/**
	 * 联系方式
	 */
	private String phone;
	
	/**
	 * 店铺头像
	 */
	private String shopImg;
	
	/**
	 * 优先级
	 */
	private Integer priority;
	
	private Date createTime;
	
	private Date lastEditTime;
	
	//状态:-1,不可用 0,审核中 1,可用
	private Integer enableStatus;
	
	//管理员给店家的通知
	private String advice;
	
	//店铺所属区域
	private Area area;
	
	//店铺所属用户
	private UserInfo owner;
	
	//所属类别
	private ShopCategory shopCategory;
	
}
