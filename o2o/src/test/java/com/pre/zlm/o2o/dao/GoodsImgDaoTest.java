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
import com.pre.zlm.o2o.entity.GoodsImg;

/**
 * 测试回环,通过添加查询和删除的顺序来依次执行,这样就能保证测试用例不出问题。
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoodsImgDaoTest extends BaseTest{
	
	@Autowired
	private GoodsImgDao dao;
	
	@Test
	public void testABatchInsertGoodsImg() {
		List<GoodsImg> list = new ArrayList<>();
		GoodsImg img1 = new GoodsImg();
		img1.setCreateTime(new Date());
		img1.setGoodsId(1L);
		img1.setImgAddr("图片1");
		img1.setImgDesc("测试1");
		img1.setPriority(2);
		list.add(img1);
		
		GoodsImg img2 = new GoodsImg();
		img2.setCreateTime(new Date());
		img2.setGoodsId(1L);
		img2.setImgAddr("图片2");
		img2.setImgDesc("测试2");
		img2.setPriority(2);
		list.add(img2);
		
		int effectedNum = dao.batchInsertGoodsImg(list);
		assertEquals(2, effectedNum);
	}
	
	@Test
	public void testBListGoodsCategoryList() {
	}
	
	@Test 
	public void testCDeleteGoodsCategory() {
	}
	
}
