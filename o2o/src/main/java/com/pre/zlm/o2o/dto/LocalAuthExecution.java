package com.pre.zlm.o2o.dto;

import com.pre.zlm.o2o.entity.LocalAuth;
import com.pre.zlm.o2o.enums.LocalAuthEnum;

import lombok.Data;

@Data
public class LocalAuthExecution {
	/**
	 * 结果状态
	 */
	private int state;
	
	/**
	 * 状态标识
	 */
	private String stateInfo;
	/**
	 * 操作的LocalAuth(增删改查时用到)
	 */
	private LocalAuth localAuth;
	
	
	public LocalAuthExecution() {
	}
	
	//店铺操作失败时调用的构造器
	public LocalAuthExecution(LocalAuthEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	//店铺操作成功时使用的构造器
	public LocalAuthExecution(LocalAuthEnum stateEnum, LocalAuth localAuth) {
		this(stateEnum);
		this.localAuth = localAuth;
	}

}
