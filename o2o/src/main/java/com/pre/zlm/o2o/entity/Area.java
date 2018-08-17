package com.pre.zlm.o2o.entity;
import java.util.Date;

import lombok.Data;
@Data
public class Area {
	/**
	 * 区域ID
	 */
	private Integer areaId;
	
	/**
	 * 区域名称
	 */
	private String areaName;
	
	/**
	 * 区域权重
	 */
	private Integer priority;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date lastEditTime;
	
}
