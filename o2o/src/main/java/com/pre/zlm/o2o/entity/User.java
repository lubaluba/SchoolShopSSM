package com.pre.zlm.o2o.entity;

import java.util.Date;

import lombok.Data;
@Data
public class User {
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 用户姓名
	 */
	private String name;
	
	/**
	 * 用户头像地址
	 */
	private String profileImg;
	
	/**
	 * 用户邮箱
	 */
	private String email;
	
	/**
	 * 性别
	 */
	private String gender;
	
	/**
	 * 用户状态
	 */
	private Integer enableStatus;
	
	/**
	 * 用户类别:1顾客, 2:店家, 3:管理员
	 */
	private Integer userType;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后修改时间
	 */
	private Date lastEditTime;
}
