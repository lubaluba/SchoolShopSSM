package com.pre.zlm.o2o.entity;

import java.util.Date;

//微信账号
public class WechatAuth {
	//微信号
	private Long wechatAuthId;
	//
	private String openId;
	//创建时间
	private Date createTime;
	//关联用户
	private UserInfo user;
	public Long getWechatAuthId() {
		return wechatAuthId;
	}
	public void setWechatAuthId(Long wechatAuthId) {
		this.wechatAuthId = wechatAuthId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
}
