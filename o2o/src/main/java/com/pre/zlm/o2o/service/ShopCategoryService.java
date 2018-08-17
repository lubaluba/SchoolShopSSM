package com.pre.zlm.o2o.service;

import java.util.List;

import com.pre.zlm.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	
	/**
	 * 获取商品类别信息
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> listShopCategory(ShopCategory shopCategoryCondition);
}
