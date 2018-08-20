package com.pre.zlm.o2o.enums;

public enum GoodsCategoryStateEnum {

	SUCCESS(1,"创建商品类别成功"),
	INNER_ERROR(-1001,"操作失败"),
	EMPTY_LIST(-1002,"添加商品类别列表失败,列表为空");
	
	private int state;
	
	private String stateInfo;
	
	private GoodsCategoryStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	
	//根据传入的state返回响应enum值
	public static GoodsCategoryStateEnum stateOf(int state) {
		for(GoodsCategoryStateEnum stateEnum:values()) {
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
