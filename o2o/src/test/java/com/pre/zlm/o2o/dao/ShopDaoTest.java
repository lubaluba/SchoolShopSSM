package com.pre.zlm.o2o.dao;
import java.util.Date;
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
