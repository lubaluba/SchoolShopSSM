package com.pre.zlm.o2o.utils.weixin.pojo;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * 	@author zlm
 * 	微信用户实体类
 */
@Data
public class WechatUser implements Serializable {

	private static final long serialVersionUID = 1L;

	//private int id;

	//标识该公众号下面该用户的唯一id
	@JsonProperty("openid")
	private String openId;

	//用户昵称
	@JsonProperty("nickname")
	private String nickName;

	//性别
	@JsonProperty("sex")
	private int sex;

	//省份
	@JsonProperty("province")
	private String province;

	//城市
	@JsonProperty("city")
	private String city;

	//区
	@JsonProperty("country")
	private String country;

	//头像图片地址
	@JsonProperty("headimgurl")
	private String headimgurl;

	//语言
	@JsonProperty("language")
	private String language;
	
	//权限
	@JsonProperty("privilege")
	private String privilege;

	//private String unionid;

	@Override
	public String toString() {
		return "openId:" + this.getOpenId() + ",nikename:" + this.getNickName();
	}
}
