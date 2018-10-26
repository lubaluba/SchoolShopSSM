package com.pre.zlm.o2o.dao;

import com.pre.zlm.o2o.entity.Goods;

public interface GoodsDao {
	
	/**
	 * 添加商品
	 */
	int insertGoods(Goods goods);
	
	/**
	 * 编辑商品
	 */
	int updateGoods(Goods goods);
	
	/**
	 * 根据id获取商品
	 */
	Goods getGoodsById(Long goodsId);

}
