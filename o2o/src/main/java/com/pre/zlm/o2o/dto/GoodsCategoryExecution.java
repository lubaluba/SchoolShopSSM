package com.pre.zlm.o2o.dto;
import java.util.List;

import com.pre.zlm.o2o.entity.GoodsCategory;
import com.pre.zlm.o2o.enums.GoodsCategoryStateEnum;

import lombok.Data;
//dto层主要适用于封装业务,有时候直接使用实体并不能满足我们的要求,其功能类似于pageBean
@Data
public class GoodsCategoryExecution {
	/**
	 * 结果状态
	 */
	private int state;
	
	/**
	 * 状态标识
	 */
	private String stateInfo;
	/**
	 * 类别数目
	 */
	private int count;
	
	/**
	 * 操作的Shop(增删改查时用到)
	 */
	private GoodsCategory goodsCategory;
	
	/**
	 * 店铺类表,批量查询和操作
	 */
	private List<GoodsCategory> goodsCategoryList;
	
	public GoodsCategoryExecution(){
	}
	
	//店铺操作失败时调用的构造器
	public GoodsCategoryExecution(GoodsCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	//店铺操作成功时使用的构造器
	public GoodsCategoryExecution(GoodsCategoryStateEnum stateEnum, GoodsCategory goodsCategory) {
		this(stateEnum);
		this.goodsCategory = goodsCategory;
	}
	//店铺操作成功时使用的构造器
	public GoodsCategoryExecution(GoodsCategoryStateEnum stateEnum, List<GoodsCategory> goodsCategoryList) { 
		this(stateEnum);
		this.goodsCategoryList = goodsCategoryList;
	}
}
