package com.pre.zlm.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pre.zlm.o2o.dao.ShopCategoryDao;
import com.pre.zlm.o2o.entity.ShopCategory;
import com.pre.zlm.o2o.service.ShopCategoryService;
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService{
	
	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Override
	public List<ShopCategory> listShopCategory(ShopCategory shopCategoryCondition) {
		
		return shopCategoryDao.listShopCategory(shopCategoryCondition);
	}
	
	
}
