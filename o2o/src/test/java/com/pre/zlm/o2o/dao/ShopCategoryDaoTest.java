package com.pre.zlm.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{
	
	@Autowired
	private ShopCategoryDao dao;
	
	@Test
	public void testListShopCategoryList() {
		List<ShopCategory> list = dao.listShopCategory(new ShopCategory());
		assertEquals(1, list.size());
		assertEquals("网球拍", list.get(0).getShopCategoryName());
		
		ShopCategory sc = new ShopCategory();
		sc.setParent(list.get(0));
		List<ShopCategory> list1 = dao.listShopCategory(sc);
		assertEquals(0, list1.size());
	}
}
