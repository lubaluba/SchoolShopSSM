package com.pre.zlm.o2o.service;

import java.util.List;

import com.pre.zlm.o2o.dto.GoodsExecution;
import com.pre.zlm.o2o.dto.ImageHolder;
import com.pre.zlm.o2o.entity.Goods;
import com.pre.zlm.o2o.exception.GoodsOperationException;

public interface GoodsService {
	
	/**
	 * 添加商品
	 */
	GoodsExecution addGoods(Goods goods, ImageHolder thumbnail, List<ImageHolder> goodsImgList) 
			throws GoodsOperationException;
}

