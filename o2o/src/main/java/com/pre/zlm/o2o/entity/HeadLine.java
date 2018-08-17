package com.pre.zlm.o2o.entity;

import java.util.Date;

import lombok.Data;

/**
 * 头条信息
 */
@Data
public class HeadLine {
	/**
	 * 头条id
	 */
	private Long lineId;
	
	/**
	 * 头条名
	 */
	private String lineName;
	
	/**
	 * 头条链接
	 */
	private String lineLink;
	
	/**
	 * 头条图片
	 */
	private String lineImg;
	
	/**
	 * 头条优先级
	 */
	private Integer priority;
	
	/**
	 * 头条状态0:不可用,1:可用
	 */
	private Integer enableStatus;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后修改时间
	 */
	private Date lastEditTime;
	
}
