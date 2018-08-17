package com.pre.zlm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pre.zlm.o2o.entity.ShopCategory;

public interface ShopCategoryDao {
	
	/**
	 * 根据店铺类别获得店铺类别
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> listShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);
}
