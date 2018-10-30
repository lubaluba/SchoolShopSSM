package com.pre.zlm.o2o.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.dto.GoodsCategoryExecution;
import com.pre.zlm.o2o.entity.GoodsCategory;
import com.pre.zlm.o2o.enums.GoodsCategoryStateEnum;

public class GoodsCategoryServiceTest extends BaseTest{
	
	@Autowired
	GoodsCategoryService service;
	
	@Test
	@Ignore
	public void testShopCategoryService() {
		List<GoodsCategory> list = service.listGoodsCategory(1L);
		assertTrue(list.size() == 2);
		assertEquals("饮料类", list.get(1).getGoodsCategoryName());
	}
	
	@Test 
	@Ignore
	public void testBatchInsertGoodsCategory() {
		List<GoodsCategory> list = new ArrayList<>();
		
		GoodsCategory c1 = new GoodsCategory();
		c1.setGoodsCategoryName("现磨咖啡");
		c1.setPriority(5);
		c1.setShopId(3L);
		c1.setCreateTime(new Date());
		list.add(c1);
		
		GoodsCategory c2 = new GoodsCategory();
		c2.setGoodsCategoryName("速溶咖啡");
		c2.setPriority(4);
		c2.setShopId(3L);
		c2.setCreateTime(new Date());
		list.add(c2);
		
		GoodsCategoryExecution gce = service.batchInsertGoodsCategory(list);
		assertEquals(1, gce.getState());
	}
	
	@Test
	@Ignore
	public void testBatchInsertGoodsCategoryFail() {
		List<GoodsCategory> list = new ArrayList<>();
		GoodsCategoryExecution gce = service.batchInsertGoodsCategory(list);
		assertEquals(-1002, gce.getState());
	}
	
	@Test
	public void testDeleteGoodsCategory() {
		GoodsCategoryExecution res = service.deleteGoodsCategory(1L, 1L);
		assertEquals(GoodsCategoryStateEnum.SUCCESS.getState(), res.getState());
	}
}
