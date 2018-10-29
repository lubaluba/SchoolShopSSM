package com.pre.zlm.o2o.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.Goods;
import com.pre.zlm.o2o.entity.GoodsCategory;
import com.pre.zlm.o2o.entity.GoodsImg;
import com.pre.zlm.o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoodsDaoTest extends BaseTest{
	@Autowired
	private GoodsDao dao;
	@Autowired
	private GoodsImgDao imgDao;
	
	@Test
	@Ignore
	public void testAInsertGoods() {
		Goods goods = new Goods();
		goods.setCreateTime(new Date());
		goods.setEnableStatus(0);
		GoodsCategory gc = new GoodsCategory();
		gc.setGoodsCategoryId(1L);
		goods.setGoodsId(1L);
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
		
		List<GoodsImg> list = new ArrayList<>();
		GoodsImg img1 = new GoodsImg();
		img1.setGoodsId(1L);
		img1.setImgAddress("11");
		img1.setGoodsId(1L);
		GoodsImg img2 = new GoodsImg();
		img2.setGoodsId(2L);
		img2.setGoodsId(1L);
		img2.setImgAddress("22");
		list.add(img1);
		list.add(img2);
		int effectNum = imgDao.batchInsertGoodsImg(list);
		assertTrue(effectNum == 2);
	}
	
	@Test
	@Ignore
	public void testBFindGoodsById() {
		Long goodsId = 1L;
		Goods goods = dao.getGoodsById(goodsId);
		assertEquals(goods.getGoodsName(), "测试啊");
		assertEquals(goods.getImgList().size(), 2);
		assertTrue(goods.getShop().getShopId() == 1L);
		assertTrue(goods.getGoodsCategory().getGoodsCategoryId() == 1L);
	}
	
	@Test
	@Ignore
	public void testCUpdateGoods() {
		Goods goods = new Goods();
		goods.setGoodsId(1L);
		goods.setGoodsName("更新啊");
		goods.setGoodsDesc("更新");
		goods.setImgAddr("地址也更新");
		goods.setNormalPrice(1111);
		goods.setPromotionPrice(2222);
		goods.setPriority(5);
		goods.setLastEditTime(new Date());
		goods.setEnableStatus(-1);
		Shop shop = new Shop();
		shop.setShopId(1L);
		goods.setShop(shop);
		int result = dao.updateGoods(goods);
		assertEquals(result, 1);
	}
	
	@Test
	@Ignore
	public void testGetGoodsCount() {
		Goods goodsCondition = new Goods();
		int countAll = dao.getGoodsCount(goodsCondition);
		assertEquals(countAll, 3);
		Goods goodsCondition2 = new Goods();
		goodsCondition2.setGoodsName("香辣");
		int countName = dao.getGoodsCount(goodsCondition2);
		assertEquals(countName, 1);
		Goods goodsCondition3 = new Goods();
		GoodsCategory gc = new GoodsCategory();
		gc.setGoodsCategoryId(1L);
		goodsCondition3.setGoodsCategory(gc);
		int countCategory = dao.getGoodsCount(goodsCondition3);
		assertEquals(countCategory, 2);
		Goods goodsCondition4 = new Goods();
		Shop shop = new Shop();
		shop.setShopId(2L);
		goodsCondition4.setShop(shop);
		int countShop= dao.getGoodsCount(goodsCondition4);
		assertEquals(countShop, 1);
	}
	
	@Test
	@Ignore
	public void testListGoods() {
		Goods goodsCondition = new Goods();
		int result = dao.listGoods(goodsCondition, 0, 3).size();
		assertEquals(result, 3);
	}
	
	@Test
	public void testUpdateGoodsCategoryToNull() {
		int result = dao.updateGoodsCategoryToNull(1L, 1L);
		assertEquals(2, result);
	}
}
