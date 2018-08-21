package com.pre.zlm.o2o.dao;

import java.util.List;

import com.pre.zlm.o2o.entity.GoodsImg;

public interface GoodsImgDao {
	
	/**
	 * 商品详情图片添加,批量添加
	 */
	int batchInsertGoodsImg(List<GoodsImg> goodsImgList);
}
