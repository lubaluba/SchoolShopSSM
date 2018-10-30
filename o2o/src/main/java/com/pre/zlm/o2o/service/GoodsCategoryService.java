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
	List<GoodsCategory> listGoodsCategory(long shopId);
	
	/**
	 * 批量插入店铺商品类别信息
	 * @param goodsCategoryList
	 * @throws GoodsCategoryOperationException
	 */
	GoodsCategoryExecution batchInsertGoodsCategory(List<GoodsCategory> goodsCategoryList) 
			throws GoodsCategoryOperationException;
			
	/**
	 *	 删除某个店铺商品类别信信息
	 *	注意:要先将该商品类别的商品下的商品的categoryId置为null,再删除类别
	 */
	GoodsCategoryExecution deleteGoodsCategory(long goodsCategoryId, long shopId)
			throws GoodsCategoryOperationException;
}
