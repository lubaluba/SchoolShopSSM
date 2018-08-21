package com.pre.zlm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pre.zlm.o2o.entity.GoodsCategory;

public interface GoodsCategoryDao {
	
	/**
	 * 根据shopId获取当前店铺下的所有商品类别信息
	 */
	List<GoodsCategory> listGoodsCategoryByShopId(long shopId);
	
	/**
	 * 商品类别批量添加
	 */
	int batchInsertGoodsCategory(List<GoodsCategory> goodsCategoryList);
	
	/**
	 * 商品类别的删除
	 */
	int deleteGoodsCategory(@Param("goodsCategoryId")long goodsCategoryId, @Param("shopId") long shopId);
}
