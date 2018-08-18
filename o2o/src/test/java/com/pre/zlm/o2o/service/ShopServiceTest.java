package com.pre.zlm.o2o.service;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.dto.ShopExecution;
import com.pre.zlm.o2o.entity.Area;
import com.pre.zlm.o2o.entity.Shop;
import com.pre.zlm.o2o.entity.ShopCategory;
import com.pre.zlm.o2o.entity.UserInfo;
import com.pre.zlm.o2o.enums.ShopStateEnum;
import com.pre.zlm.o2o.exception.ShopOperationException;
public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService service;
	
	/**
	 * 通过店铺类别查询
	 */
	@Test
	public void testGetShopListByShopCategory() {
		ShopCategory sp = new ShopCategory();
		sp.setShopCategoryId(3L);
		Shop shopCondition = new Shop();
		shopCondition.setShopCategory(sp);
		ShopExecution se = service.listShopByCondition(shopCondition, 1, 1);
		assertEquals(1, se.getState());
		assertEquals(3, se.getCount());
		List<Shop> list = se.getShopList();
		assertEquals(1, list.size());
		assertEquals("吃鸡小铺", list.get(0).getShopName());
	}
	
	/**
	 * 测试非法
	 */
	public void testIlleage() {
		ShopCategory sp = new ShopCategory();
		sp.setShopCategoryId(111L);
		Shop shopCondition = new Shop();
		shopCondition.setShopCategory(sp);
		ShopExecution se = service.listShopByCondition(shopCondition, 1, 1);
		assertEquals(-1005, se.getState());
	}
	
	@Test
	@Ignore
	public void testUpdateShop() throws ShopOperationException,FileNotFoundException{
		Shop shop = service.getShopById(11L);
		assertEquals("ww", shop.getShopName());
		shop.setShopName("吃鸡小铺");
		ShopExecution shopExecution = service.updateShop(shop, null, null);
		assertEquals("操作成功", shopExecution.getStateInfo());
		assertEquals("吃鸡小铺", shopExecution.getShop().getShopName());
	}
	
	@Test
	@Ignore
	public void addShopTest() throws FileNotFoundException {
		Shop shop =new Shop();
		shop.setShopName("ww");
		shop.setShopDesc("sss");
		shop.setShopAddr("sds");
		shop.setPhone("sss");
		shop.setPriority(0);
		shop.setAdvice("感谢加入,您的店铺正在审核中");
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		Area a =new Area();
		a.setAreaId(2);
		ShopCategory sc =new ShopCategory();
		sc.setShopCategoryId(1L);
		UserInfo user =new UserInfo();
		user.setUserId(1L);
		shop.setArea(a);
		shop.setOwner(user);
		File shopImg=new File("C:/360极速浏览器下载/壁纸/05fdab5e0254bfaa2cdf08b5a030209b.jpg");
		InputStream in=new FileInputStream(shopImg);
		shop.setShopCategory(sc);
		ShopExecution se =service.addShop(shop,in,shopImg.getName());
		assertEquals(se.getState(), ShopStateEnum.CHECK.getState());
	}
}
