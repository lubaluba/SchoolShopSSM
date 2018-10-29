package com.pre.zlm.o2o.dao;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.Area;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.entity.ShopCategory;
import com.pre.zlm.o2o.entity.UserInfo;
public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao dao;
	
	/**
	 * 通过区域查询	店铺
	 */	
	@Test
	@Ignore
	public void testgetShopList() {
		UserInfo user = new UserInfo();
		user.setUserId(1L);
		Shop shopCondition = new Shop();
		shopCondition.setOwner(user);
		int rows =  dao.getShopListCount(shopCondition);
		assertEquals(7, rows);
		List<Shop> list = dao.getShopList(shopCondition, 1, 2);
		assertEquals(2, list.size());
		assertEquals("咖啡", list.get(1).getShopName());
		
	}
	
	/**
	 * 通过父类查询
	 */
	@Test
	public void testGetShopListByParent() {
		Shop shopCondition = new Shop();
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setParent(new ShopCategory(2L));
		shopCondition.setShopCategory(shopCategory);
		int res = dao.getShopListCount(shopCondition);
		assertEquals(1, res);
	}
	
	/**
	 * 通过店铺类别查询
	 */
	@Test
	@Ignore
	public void testGetShopListByShopCategory() {
		ShopCategory sp = new ShopCategory();
		sp.setShopCategoryId(3L);
		Shop shopCondition = new Shop();
		shopCondition.setShopCategory(sp);
		int rows =  dao.getShopListCount(shopCondition);
		assertEquals(3, rows);
		List<Shop> list = dao.getShopList(shopCondition, 1, 2);
		assertEquals(2, list.size());
		assertEquals("ss", list.get(1).getShopName());
		
	}
	/**
	 * 根据店铺区域查询
	 */
	@Test
	@Ignore
	public void testGetShopListByArea() {
		Area area = new Area();
		area.setAreaId(1);
		Shop shopCondition = new Shop();
		shopCondition.setArea(area);
		int rows =  dao.getShopListCount(shopCondition);
		assertEquals(2, rows);
		List<Shop> list = dao.getShopList(shopCondition, 0, 2);
		assertEquals(2, list.size());
		assertEquals("咖啡", list.get(1).getShopName());
		
	}
	
	/**
	 * 根据店铺状态查询
	 */
	@Test
	@Ignore
	public void testGetShopListByStatus() {
		Shop shopCondition = new Shop();
		shopCondition.setEnableStatus(0);
		int rows =  dao.getShopListCount(shopCondition);
		assertEquals(7, rows);
		List<Shop> list = dao.getShopList(shopCondition, 0, 2);
		assertEquals(2, list.size());
		assertEquals("吃鸡小铺", list.get(0).getShopName());
		
	}
	
	/**
	 * 根据店铺状态查询
	 */
	@Test
	@Ignore
	public void testGetShopListByName() {
		Shop shopCondition = new Shop();
		shopCondition.setShopName("吃鸡");
		int rows =  dao.getShopListCount(shopCondition);
		assertEquals(1, rows);
		List<Shop> list = dao.getShopList(shopCondition, 0, 1);
		assertEquals(1, list.size());
		assertEquals("吃鸡小铺", list.get(0).getShopName());
		
	}
	
	@Test
	@Ignore
	public void testGetShopById() {
		Shop shop = dao.getShopById(11L);
		assertEquals("ww", shop.getShopName());
		assertEquals("渔湾市", shop.getArea().getAreaName());
		assertEquals("体育用品", shop.getShopCategory().getShopCategoryName());
	}
	
	@Test
	@Ignore
	public void insertTest() {
		Shop shop =new Shop();
		shop.setShopName("ww");
		shop.setShopDesc("sss");
		shop.setShopAddr("sds");
		shop.setPhone("sss");
		shop.setShopImg("sdsd");
		shop.setPriority(4);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(-1);
		shop.setAdvice("ss");
		Area a =new Area();
		a.setAreaId(2);
		ShopCategory sc =new ShopCategory();
		sc.setShopCategoryId(1L);
		UserInfo user =new UserInfo();
		user.setUserId(1L);
		shop.setArea(a);
		shop.setOwner(user);
		shop.setShopCategory(sc);
		dao.insertShop(shop);
	}
	
	@Test
	@Ignore
	public void updateTest() {
		Shop shop =new Shop();
		shop.setShopName("试试手");
		shop.setShopDesc("s四大欠王");
		shop.setShopAddr("sds");
		shop.setPhone("sss");
		shop.setShopImg("sdsd");
		shop.setPriority(4);
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(2);
		shop.setAdvice("ss");
		Area a =new Area();
		a.setAreaId(2);
		ShopCategory sc =new ShopCategory();
		sc.setShopCategoryId(1L);
		UserInfo user =new UserInfo();
		user.setUserId(1L);
		shop.setArea(a);
		shop.setOwner(user);
		shop.setShopId(1L);
		shop.setShopCategory(sc);
		dao.updateShop(shop);
	}

}
