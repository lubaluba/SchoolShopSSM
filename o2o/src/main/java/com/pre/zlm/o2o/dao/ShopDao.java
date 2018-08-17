package com.pre.zlm.o2o.dao;
import com.pre.zlm.o2o.entity.Shop;
public interface ShopDao {
	
	/**
	 * 新增店铺
	 */
	int insertShop(Shop shop);
	
	/**
	 * 更新店铺信息
	 */
	int updateShop(Shop shop);
	
	/**
	 * 根据shopId查询店铺信息
	 */
	Shop getShopById(Long shopId);
}
