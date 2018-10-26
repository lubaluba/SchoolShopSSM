package com.pre.zlm.o2o.dao;

import java.util.List;

import com.pre.zlm.o2o.entity.GoodsImg;

public interface GoodsImgDao {
	
	/**
	 * 商品详情图片添加,批量添加
	 */
	int batchInsertGoodsImg(List<GoodsImg> goodsImgList);
	
	/**
	 * 查询商品详情图片
	 */
	List<GoodsImg> getGoodsImgList(Long goodsId);
	
	/**
	 * 根据goodsId批量删除该商品下面的全部图片
	 */
	int deleteGoodsImgByGoodsId(Long goodsId);
}

