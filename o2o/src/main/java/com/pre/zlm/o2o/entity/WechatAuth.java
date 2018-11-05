package com.pre.zlm.o2o.entity;
import java.util.Date;

import lombok.Data;

/**
 * 微信账号
 * @author a3325
 *
 */
@Data
public class WechatAuth {
	/**
	 * 微信号
	 */
	private Long wechatAuthId;
	
	/**
	 * 账户
	 */
	private String openId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 关联用户
	 */
	private User user;
}
