package com.pre.zlm.o2o.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.ShopCategory;

public class ShopCategoryServiceTest extends BaseTest{
	
	@Autowired
	ShopCategoryService service;
	
	@Test
	public void testShopCategoryService() {
		List<ShopCategory> list = service.listShopCategory(new ShopCategory());
		assertTrue(list.size() == 1);
	}
}
