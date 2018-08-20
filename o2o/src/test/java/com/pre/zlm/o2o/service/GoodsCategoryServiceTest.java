package com.pre.zlm.o2o.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.GoodsCategory;

public class GoodsCategoryServiceTest extends BaseTest{
	
	@Autowired
	GoodsCategoryService service;
	
	@Test
	public void testShopCategoryService() {
		List<GoodsCategory> list = service.listShopCategory(1L);
		assertTrue(list.size() == 2);
		assertEquals("饮料类", list.get(1).getGoodsCategoryName());
	}
}
