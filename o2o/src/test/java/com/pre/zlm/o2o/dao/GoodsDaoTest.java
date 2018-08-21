package com.pre.zlm.o2o.dao;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.Goods;
import com.pre.zlm.o2o.entity.GoodsCategory;
import com.pre.zlm.o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoodsDaoTest extends BaseTest{
	@Autowired
	private GoodsDao dao;
	
	@Test
	public void testAInsertGoods() {
		Goods goods = new Goods();
		goods.setCreateTime(new Date());
		goods.setEnableStatus(0);
		GoodsCategory gc = new GoodsCategory();
		gc.setGoodsCategoryId(1L);
		goods.setGoodsCategory(gc);
		goods.setGoodsDesc("测试用的");
		goods.setGoodsName("测试啊");
		goods.setImgAddr("都说了测试嘛");
		goods.setLastEditTime(new Date());
		goods.setNormalPrice(1000);
		goods.setPriority(2);
		goods.setPromotionPrice(800);
		Shop shop = new Shop();
		shop.setShopId(1L);
		goods.setShop(shop);
		
		int effectedRow = dao.insertGoods(goods);
		assertEquals(1, effectedRow);
	}

}
