package com.pre.zlm.o2o.service;

import java.util.List;

import com.pre.zlm.o2o.entity.GoodsCategory;

public interface GoodsCategoryService {
	
	/**
	 * 根据店铺id获取商品类别信息
	 * @param long shopId
	 * @return
	 */
	List<GoodsCategory> listShopCategory(long shopId);
}
