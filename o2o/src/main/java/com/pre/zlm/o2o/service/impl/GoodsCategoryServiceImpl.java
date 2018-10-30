package com.pre.zlm.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pre.zlm.o2o.dao.GoodsCategoryDao;
import com.pre.zlm.o2o.dao.GoodsDao;
import com.pre.zlm.o2o.dto.GoodsCategoryExecution;
import com.pre.zlm.o2o.entity.Goods;
import com.pre.zlm.o2o.entity.GoodsCategory;
import com.pre.zlm.o2o.enums.GoodsCategoryStateEnum;
import com.pre.zlm.o2o.exception.GoodsCategoryOperationException;
import com.pre.zlm.o2o.service.GoodsCategoryService;
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
	
	@Autowired
	private GoodsCategoryDao goodsCategoryDao;
	
	@Autowired
	private GoodsDao goodsDao;

	@Override
	public List<GoodsCategory> listGoodsCategory(long shopId) {
		
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

	/**
	 * 删除商品类别信息
	 */
	@Override
	@Transactional
	public GoodsCategoryExecution deleteGoodsCategory(long goodsCategoryId, long shopId)
			throws GoodsCategoryOperationException {
		//将此商品类别下的商品的类别id置为空
		Goods goodsCondition = new Goods();
		goodsCondition.setGoodsCategory(new GoodsCategory(goodsCategoryId));
		int count = goodsDao.getGoodsCount(goodsCondition);
		int res = goodsDao.updateGoodsCategoryToNull(goodsCategoryId, shopId);
		if (count != res) {
			throw new GoodsCategoryOperationException("解除商品和商品类别关系失败");
		}
		try {
			int effectedNum  = goodsCategoryDao.deleteGoodsCategory(goodsCategoryId, shopId);
			if (effectedNum <= 0) {
				throw new GoodsCategoryOperationException("商品类别删除失败");
			} else {
				return new GoodsCategoryExecution(GoodsCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			throw new GoodsCategoryOperationException("deleteGoodsCategory error" + e.getMessage());
		}
	}

	
	
}
