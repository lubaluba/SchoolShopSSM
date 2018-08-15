package com.pre.zlm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pre.zlm.o2o.entity.ShopCategory;

public interface ShopCategoryDao {
	
	List<ShopCategory> listShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);
}
