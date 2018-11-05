package com.pre.zlm.o2o.enums;

public enum WechatAuthEnum {
	
	SUCCESS(1,"操作成功"),
	INNER_ERROR(-1001,"操作失败"),
	EMPTY_PARAM(-1002,"插入失败,检查参数");
	
	private int state;
	
	private String stateInfo;
	
	private WechatAuthEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	
	public static WechatAuthEnum stateOf(int state) {
		for(WechatAuthEnum stateEnum:values()) {
			if(stateEnum.getState()==state)
				return stateEnum;
		}
		return null;
	}
	public int getState() {
		return state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
}
