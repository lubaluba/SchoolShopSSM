package com.pre.zlm.o2o.service;

import java.util.List;

import com.pre.zlm.o2o.dto.GoodsCategoryExecution;
import com.pre.zlm.o2o.entity.GoodsCategory;
import com.pre.zlm.o2o.exception.GoodsCategoryOperationException;

public interface GoodsCategoryService {
	
	/**
	 * 根据店铺id获取商品类别信息
	 * @param long shopId
	 * @return
	 */
	List<GoodsCategory> listShopCategory(long shopId);
	
	/**
	 * 批量插入店铺商品类别信息
	 * @param goodsCategoryList
	 * @throws GoodsCategoryOperationException
	 */
	GoodsCategoryExecution batchInsertGoodsCategory(List<GoodsCategory> goodsCategoryList) 
			throws GoodsCategoryOperationException;
}
