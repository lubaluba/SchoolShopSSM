package com.pre.zlm.o2o.enums;

public enum LocalAuthEnum {
	SUCCESS(0,"操作成功"),
	ONLY_ONE_ACCOUNT(-1001,"平台账号已存在"),
	EMPTY(-1002,"localAuth信息为空"),
	UPDATE_ERROR(-1003,"账号更新失败"),
	QUERY_NULL(-1005, "查询为空,请检查查询条件"),
	BIND_FAIL(-1006,"绑定账号失败"),
	PARAM_NULL(-1007,"传入参数为空");
	
	private int state;
	
	private String stateInfo;
	
	private LocalAuthEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	//根据传入的state返回响应enum值
	public static LocalAuthEnum stateOf(int state) {
		for(LocalAuthEnum stateEnum:values()) {
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
