package com.pre.zlm.o2o.service;

import java.util.List;

import com.pre.zlm.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	
	List<ShopCategory> listShopCategory(ShopCategory shopCategoryCondition);
}
