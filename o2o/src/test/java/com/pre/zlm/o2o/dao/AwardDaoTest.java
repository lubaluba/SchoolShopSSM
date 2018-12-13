package com.pre.zlm.o2o.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.Award;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AwardDaoTest extends BaseTest{

	@Autowired
	private AwardDao awardDao;
	
	@Test
	public void testAInsertAward() {
		Award award = new Award();
		award.setAwardId(1L);
		award.setAwardDesc("测试");
		award.setAwardImg("测试");
		award.setAwardName("测试");
		award.setCreateTime(new Date());
		award.setEnableStatus(1);
		award.setLastEditTime(new Date());
		award.setPoint(10);
		award.setPriority(2);
		award.setShopId(1L);
		int effectedRow = awardDao.insertAward(award);
		assertTrue(effectedRow == 1);
	}
	
	@Test
	public void testBgetCountAndList() {
		Award condition1 = new Award();
		condition1.setAwardName("测试");
		int r11 = awardDao.getAwardCount(condition1);
		int r12 = awardDao.listAwardDaoByPage(condition1, 0, 2).size();
		assertEquals(r11, 1);
		assertEquals(r12, 1);
		
		Award condition2 = new Award();
		condition2.setEnableStatus(1);
		int r21 = awardDao.getAwardCount(condition2);
		int r22 = awardDao.listAwardDaoByPage(condition2, 0, 2).size();
		assertEquals(r21, 1);
		assertEquals(r22, 1);
		
		Award condition3 = new Award();
		condition3.setShopId(1L);
		int r31 = awardDao.getAwardCount(condition3);
		int r32 = awardDao.listAwardDaoByPage(condition3, 0, 2).size();
		assertEquals(r31, 1);
		assertEquals(r32, 1);
	}
	
	@Test
	public void testCgetAwardById() {
		Award award = awardDao.getAwardById(1L);
		assertEquals(award.getAwardName(), "测试");
		assertTrue(award.getShopId() == 1L);
	}
	
	@Test
	public void testDUpdateGoods() {
		Award award = new Award();
		award.setAwardDesc("测试2");
		award.setAwardImg("测试2");
		award.setAwardName("测试2");
		award.setCreateTime(new Date());
		award.setEnableStatus(1);
		award.setLastEditTime(new Date());
		award.setPoint(10);
		award.setAwardId(1L);
		award.setPriority(2);
		award.setShopId(1L);
		int result = awardDao.updateAward(award);
		assertEquals(result, 1);
	}
	
	@Test
	public void testFEeleteAward() {
		int effectNum = awardDao.deleteAward(1L, 1L);
		assertEquals(effectNum, 1);
	}
	
/*	@Test
	@Ignore
	public void testDGetGoodsCount() {
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
	}*/
	
}
