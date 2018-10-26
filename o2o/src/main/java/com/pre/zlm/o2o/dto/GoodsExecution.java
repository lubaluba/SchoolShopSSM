package com.pre.zlm.o2o.dto;
import java.util.List;

import com.pre.zlm.o2o.entity.Goods;
import com.pre.zlm.o2o.enums.GoodsStateEnum;

import lombok.Data;
//dto层主要适用于封装业务,有时候直接使用实体并不能满足我们的要求,其功能类似于pageBean
@Data
public class GoodsExecution {
	/**
	 * 结果状态
	 */
	private int state;
	
	/**
	 * 状态标识
	 */
	private String stateInfo;
	/**
	 * 商品数目
	 */
	private int count;
	
	/**
	 * 操作的goods(增删改查时用到)
	 */
	private Goods goods;
	
	/**
	 * 商品类表,批量查询和操作
	 */
	private List<Goods> goodsList;
	
	public GoodsExecution(){
	}
	
	public GoodsExecution(GoodsStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public GoodsExecution(GoodsStateEnum stateEnum, Goods goods) {
		this(stateEnum);
		this.goods = goods;
	}

	public GoodsExecution(GoodsStateEnum stateEnum, List<Goods> goodsList) {
		this(stateEnum);
		this.goodsList = goodsList;
	}
}
