package com.pre.zlm.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.dto.Cart;
import com.pre.zlm.o2o.dto.Order;

public class CartServiceTest extends BaseTest {
	
	@Autowired
	CartService service;
	
	@Test
	@Ignore
	public void testGetCart() {
		Cart cart = service.getUserCart(1L);
		assertEquals(cart.getGoodsList().size(), 3);
	}
	
	@Test
	public void testGetOrder() {
		List<Order> list = service.getCustomerOrders(2L);
		assertEquals(list.size(), 3);
	}
}
