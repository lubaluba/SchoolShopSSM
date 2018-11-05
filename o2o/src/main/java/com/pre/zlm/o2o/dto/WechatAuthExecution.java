package com.pre.zlm.o2o.dto;

import java.util.List;

import com.pre.zlm.o2o.entity.WechatAuth;
import com.pre.zlm.o2o.enums.WechatAuthEnum;

import lombok.Data;

@Data
public class WechatAuthExecution {
	/**
	 * 	结果状态
	 */
	private int state;
	
	/**
	 * 	状态标识
	 */
	private String stateInfo;
	/**
	 *	返回数目
	 */
	private int count;
	
	/**
	 * 操作的wechatAuth(增删改查时用到)
	 */
	private WechatAuth wechatAuth;
	
	/**
	 * 账号列表,批量查询和操作
	 */
	private List<WechatAuth> wechatAuthList;
	
	public WechatAuthExecution(){
	}
	
	public WechatAuthExecution(WechatAuthEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public WechatAuthExecution(WechatAuthEnum stateEnum, WechatAuth wechatAuth) {
		this(stateEnum);
		this.wechatAuth = wechatAuth;
	}

	public WechatAuthExecution(WechatAuthEnum stateEnum, List<WechatAuth> wechatAuthList) {
		this(stateEnum);
		this.wechatAuthList = wechatAuthList;
	}
}
