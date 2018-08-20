package com.pre.zlm.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pre.zlm.o2o.dao.GoodsCategoryDao;
import com.pre.zlm.o2o.entity.GoodsCategory;
import com.pre.zlm.o2o.service.GoodsCategoryService;
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
	
	@Autowired
	private GoodsCategoryDao goodsCategoryDao;

	@Override
	public List<GoodsCategory> listShopCategory(long shopId) {
		
		return goodsCategoryDao.listGoodsCategoryByShopId(shopId);
	}

	
	
}
