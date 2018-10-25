package com.pre.zlm.o2o.enums;

public enum ShopStateEnum {
	
	CHECK(0, "审核中"),
	OFFLINE(-1, "非法店铺"),
	SUCCESS(1, "操作成功"),
	PASS(2, "通过认证"),
	INNER_ERROR(-1001, "程序内部错误"),
	NULL_SHOP(-1002, "shop信息为空"),
	NULL_SHOPNAME(-1003, "店铺名为空"),
	UPDATE_ERROR(-1004, "店铺更新失败"),
	QUERY_NULL(-1005, "查询为空,请检查查询条件");
	
	private int state;
	
	private String stateInfo;
	
	private ShopStateEnum(int state, String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	//根据传入的state返回响应enum值
	public static ShopStateEnum stateOf(int state) {
		for(ShopStateEnum stateEnum : values()) {
			if(stateEnum.getState() == state)
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
