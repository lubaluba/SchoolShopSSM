package com.pre.zlm.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.GoodsCategory;

/**
 * 测试回环,通过添加查询和删除的顺序来依次执行,这样就能保证测试用例不出问题。
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoodsCategoryDaoTest extends BaseTest{
	
	@Autowired
	private GoodsCategoryDao dao;
	
	@Test
	public void testBListGoodsCategoryList() {
		List<GoodsCategory> list = dao.listGoodsCategoryByShopId(14L);
		assertEquals(1, list.size());
		assertEquals("测试用例", list.get(0).getGoodsCategoryName());
	}
	
	@Test 
	public void testCDeleteGoodsCategory() {
		long goodsCategoryId = 16L;
		long shopId = 14L;
		int effectRows = dao.deleteGoodsCategory(goodsCategoryId, shopId);
		assertEquals(1, effectRows);
	}
	
	@Test
	public void testABatchInsertGoodsCategory() {
		
		List<GoodsCategory> list = new ArrayList<>();
		GoodsCategory c1 = new GoodsCategory();
		c1.setGoodsCategoryName("测试用例");
		c1.setPriority(2);
		c1.setCreateTime(new Date());
		c1.setShopId(14L);
		list.add(c1);
		int effectRows = dao.batchInsertGoodsCategory(list);
		assertEquals(1, effectRows);
		
		/*List<GoodsCategory> list = new ArrayList<>();
		
		GoodsCategory c1 = new GoodsCategory();
		c1.setGoodsCategoryName("浣纱绿(原味绿茶)");
		c1.setPriority(5);
		c1.setShopId(2L);
		c1.setCreateTime(new Date());
		list.add(c1);
		
		GoodsCategory c2 = new GoodsCategory();
		c2.setGoodsCategoryName("浣纱绿(绿茶&纯牛奶)");
		c2.setPriority(4);
		c2.setShopId(2L);
		c2.setCreateTime(new Date());
		list.add(c2);
		
		GoodsCategory c3 = new GoodsCategory();
		c3.setGoodsCategoryName("红颜(原叶红茶)");
		c3.setPriority(3);
		c3.setShopId(2L);
		c3.setCreateTime(new Date());
		list.add(c3);
		
		GoodsCategory c4 = new GoodsCategory();
		c4.setGoodsCategoryName("红颜(红茶&纯牛奶)");
		c4.setPriority(2);
		c4.setShopId(2L);
		c4.setCreateTime(new Date());
		list.add(c4);
		
		GoodsCategory c5 = new GoodsCategory();
		c5.setGoodsCategoryName("豆蔻(花果茶)");
		c5.setPriority(1);
		c5.setShopId(2L);
		c5.setCreateTime(new Date());
		list.add(c5);
		
		int effectRows = dao.batchInsertGoodsCategory(list);
		assertEquals(5, effectRows);*/
	}
}
