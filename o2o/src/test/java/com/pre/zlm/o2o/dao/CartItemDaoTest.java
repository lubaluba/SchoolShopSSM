package com.pre.zlm.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.CartItem;


public class CartItemDaoTest extends BaseTest {
	
	@Autowired
	private CartItemDao dao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Test
	public void testUpdateStatus() {
		int res = dao.updateStatus(1, 2L);
		assertEquals(res, 4);
	}
	
	@Test
	@Ignore
	public void testGetCart() {
		List<CartItem> list = dao.getCartByUser(2L);
		assertEquals(list.size(), 3);
	}

	@Test
	@Ignore
	public void testInsert() {
		CartItem item = new CartItem();
		item.setItemId(UUID.randomUUID().toString());
		item.setGoods(goodsDao.getGoodsById(1L));
		item.setNumber(10);
		item.setStatus(0);
		item.setPrice(2200.00);
		item.setUserId(1L);
		int res = dao.addGoodsIntoCart(item);
		assertEquals(res, 1);
	}
}

