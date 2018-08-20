package com.pre.zlm.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.GoodsCategory;

public class GoodsCategoryDaoTest extends BaseTest{
	
	@Autowired
	private GoodsCategoryDao dao;
	
	@Test
	public void testListShopCategoryList() {
		List<GoodsCategory> list = dao.listGoodsCategoryByShopId(1L);
		assertEquals(2, list.size());
		assertEquals("炸鸡类", list.get(0).getGoodsCategoryName());
	}
}
