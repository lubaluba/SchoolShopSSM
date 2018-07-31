package com.pre.zlm.o2o.entity;

import java.util.Date;

//本地账户
public class LocalAuth {
	//账户id
	private Long localAuthId;
	//用户姓名
	private String username;
	//用户密码
	private String password;
	//创建时间
	private Date createTime;
	//最后修改时间
	private Date lastEditTime;
	//关联用户
	private UserInfo user;
	public Long getLocalAuthId() {
		return localAuthId;
	}
	public void setLocalAuthId(Long localAuthId) {
		this.localAuthId = localAuthId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
}
