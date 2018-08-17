package com.pre.zlm.o2o.dto;
import java.util.List;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.enums.ShopStateEnum;

import lombok.Data;
//dto层主要适用于封装业务,有时候直接使用实体并不能满足我们的要求,其功能类似于pageBean
@Data
public class ShopExecution {
	/**
	 * 结果状态
	 */
	private int state;
	
	/**
	 * 状态标识
	 */
	private String stateInfo;
	/**
	 * 店铺数目
	 */
	private int count;
	
	/**
	 * 操作的Shop(增删改查时用到)
	 */
	private Shop shop;
	
	/**
	 * 店铺类表,批量查询和操作
	 */
	private List<Shop> shopList;
	
	public ShopExecution(){
	}
	
	//店铺操作失败时调用的构造器
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	//店铺操作成功时使用的构造器
	public ShopExecution(ShopStateEnum stateEnum,Shop shop) {
		this(stateEnum);
		this.shop = shop;
	}
	//店铺操作成功时使用的构造器
	public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList) {
		this(stateEnum);
		this.shopList = shopList;
	}
}
