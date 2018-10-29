package com.pre.zlm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	
	/**
	 * 商品列表展示
	 */
	List<Goods> listGoods(@Param("goodsCondition") Goods goodsCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	/**
	 * 获得查询的商品总数
	 */
	int getGoodsCount(@Param("goodsCondition") Goods goodsCondition);
}

