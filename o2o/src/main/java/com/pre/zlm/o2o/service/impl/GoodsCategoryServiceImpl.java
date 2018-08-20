package com.pre.zlm.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pre.zlm.o2o.dao.GoodsCategoryDao;
import com.pre.zlm.o2o.dto.GoodsCategoryExecution;
import com.pre.zlm.o2o.entity.GoodsCategory;
import com.pre.zlm.o2o.enums.GoodsCategoryStateEnum;
import com.pre.zlm.o2o.exception.GoodsCategoryOperationException;
import com.pre.zlm.o2o.service.GoodsCategoryService;
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
	
	@Autowired
	private GoodsCategoryDao goodsCategoryDao;

	@Override
	public List<GoodsCategory> listShopCategory(long shopId) {
		
		return goodsCategoryDao.listGoodsCategoryByShopId(shopId);
	}

	@Override
	@Transactional
	public GoodsCategoryExecution batchInsertGoodsCategory(List<GoodsCategory> goodsCategoryList)
			throws GoodsCategoryOperationException {
		if(goodsCategoryList != null && goodsCategoryList.size() > 0) {
			try {
				int effectedNum = goodsCategoryDao.batchInsertGoodsCategory(goodsCategoryList);
				if (effectedNum <= 0) {
					throw new GoodsCategoryOperationException("店铺商品类别添加失败");
				} else {
					return new GoodsCategoryExecution(GoodsCategoryStateEnum.SUCCESS);
				}
			} catch (Exception e) {
				throw new GoodsCategoryOperationException("batchInsertGoodsCategory error" + e.getMessage());
			}
		} else {
			return new GoodsCategoryExecution(GoodsCategoryStateEnum.EMPTY_LIST);
		}
	}

	
	
}
