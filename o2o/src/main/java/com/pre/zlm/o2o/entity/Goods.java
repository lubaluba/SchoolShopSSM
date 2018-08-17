package com.pre.zlm.o2o.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;
@Data
public class Goods {
	/**
	 * 商品id
	 */
	private Long goodsId;
	
	/**
	 * 商品名称
	 */
	private String goodsName;
	
	/**
	 * 详情
	 */
	private String goodsDesc;
	
	/**
	 * 图片地址
	 */
	private String imgAddr;
	
	/**
	 * 原价
	 */
	private double normalPrice;
	
	/**
	 * 优惠价格
	 */
	private double promotionPrice;
	
	/**
	 * 商品权重
	 */
	private Integer priority;
	
	/**
	 *	创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后修改时间
	 */
	private Date lastEditTime;
	
	/**
	 * 商品状态
	 * -1:不可用, 0:下架 , 1:在前端展示系统展示
	 */
	private Integer enableStatus;
	
	/**
	 * 商品详情图片列表
	 */
	private List<GoodsImg> imgList;
	
	/**
	 * 商品类别
	 */
	private GoodsCategory goodsCategory;
	
	/**
	 * 所属店铺
	 */
	private Shop shop;
}
