package com.pre.zlm.o2o.service;

import com.pre.zlm.o2o.dto.ImageHolder;
import com.pre.zlm.o2o.dto.ShopExecution;
import com.pre.zlm.o2o.entity.Shop;

public interface ShopService {
	/**
	 *	添加店铺
	 */
	ShopExecution addShop(Shop shop, ImageHolder shopImg);
	
	/**
	 * 根据店铺id获取店铺信息
	 */
	Shop getShopById(Long shopId);
	
	/**
	 * 更新店铺信息
	 */
	ShopExecution updateShop(Shop shop, ImageHolder shopImg);
	
	/**
	 * 查询店铺列表
	 */
	ShopExecution listShopByCondition(Shop shopCondition, int pageIndex, int pageSize);
}
