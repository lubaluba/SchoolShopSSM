package com.pre.zlm.o2o.entity;
import java.util.Date;

import lombok.Data;
/**
 * 商品详情图片
 */
@Data
public class GoodsImg {
	/**
	 * 图片id
	 */
	private Long goodsImgId;
	
	/**
	 * 图片地址
	 */
	private String imgAddress;
	
	/**
	 * 图片描述
	 */
	private String imgDesc;
	
	/**
	 * 权重
	 */
	private Integer priority;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 所属商品id
	 */
	private Long goodsId;
	
}
