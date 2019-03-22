package com.pre.zlm.o2o.entity;

import java.util.Date;

import lombok.Data;

/**
 * 本地账户
 */
@Data
public class LocalAuth {
	/**
	 * 账户id
	 */
	private Long localAuthId;
	
	/**
	 * 用户姓名
	 */
	private String username;
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后修改时间
	 */
	private Date lastEditTime;
	
	/**
	 * 关联用户
	 */
	private User user;
	
	/**
	 * 鉴权token
	 */
	private String token;
	
}
