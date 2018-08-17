package com.pre.zlm.o2o.service;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
import com.pre.zlm.o2o.service.ShopService;
public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService service;
	
	@Test
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
