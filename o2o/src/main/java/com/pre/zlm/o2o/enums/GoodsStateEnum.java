package com.pre.zlm.o2o.enums;

public enum GoodsStateEnum {
	
	CHECK(0,"商品下架"),
	SUCCESS(1,"操作成功"),
	INNER_ERROR(-1001,"程序内部错误"),
	EMPTY(-1002,"goods信息为空"),
	UPDATE_ERROR(-1003,"店铺更新失败"),
	QUERY_NULL(-1005, "查询为空,请检查查询条件");
	
	private int state;
	
	private String stateInfo;
	
	private GoodsStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	//根据传入的state返回响应enum值
	public static GoodsStateEnum stateOf(int state) {
		for(GoodsStateEnum stateEnum:values()) {
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
