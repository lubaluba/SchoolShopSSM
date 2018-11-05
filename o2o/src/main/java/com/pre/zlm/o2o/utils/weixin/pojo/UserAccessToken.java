package com.pre.zlm.o2o.utils.weixin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 	用户授权token
 * 	@author zlm
 */
@Data
public class UserAccessToken {

	/**
	 * 	获取到的凭证
	 */
	@JsonProperty("access_token")
	private String accessToken;
	
	/**
	 * 	凭证有效时间,单位:秒
	 */
	@JsonProperty("expires_in")
	private String expiresIn;
	
	/**
	 * 	表示更新令牌,用于获取下一次访问令牌,这里没什么用
	 */
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	/**
	 * 	该用户在此公众号下的身份标识,对于此微信号具有唯一性
	 */
	@JsonProperty("openid")
	private String openId;
	
	/**
	 * 	表示权限范围,可以忽略
	 */
	@JsonProperty("scope")
	private String scope;
	
	@Override
	public String toString() {
		return "accessToken:"+this.getAccessToken()+",openId:"+this.getOpenId();
	}
	
}
