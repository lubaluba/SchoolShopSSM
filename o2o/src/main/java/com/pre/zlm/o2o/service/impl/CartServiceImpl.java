package com.pre.zlm.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pre.zlm.o2o.dao.CartItemDao;
import com.pre.zlm.o2o.dao.ExpenseRecordDao;
import com.pre.zlm.o2o.dao.ShopDao;
import com.pre.zlm.o2o.dao.UserDao;
import com.pre.zlm.o2o.dto.Cart;
import com.pre.zlm.o2o.dto.Order;
import com.pre.zlm.o2o.entity.CartItem;
import com.pre.zlm.o2o.entity.ExpenseRecord;
import com.pre.zlm.o2o.entity.Goods;
import com.pre.zlm.o2o.service.CartService;
import com.pre.zlm.o2o.utils.UUIDUtils;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartItemDao cartDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ShopDao dao;
	
	@Autowired
	private ExpenseRecordDao recordDao;
	
	@Override
	public Cart getUserCart(Long userId) {
		Cart cart = new Cart();
		List<CartItem> list  = cartDao.getCartByUser(userId);
		for(int i = 0; i < list.size(); i++) {
			CartItem item = list.get(i);
			item.setShop(dao.getShopById(item.getGoods().getShop().getShopId()));
		}
		cart.setGoodsList(list);
		cart.setUser(userDao.getUserById(userId));
		setTotal(cart);
		return cart;
	}
	
	private void setTotal(Cart cart) {
		Double totalPrice = 0.0;
		int total = 0;
		for(CartItem item : cart.getGoodsList() ) {
			totalPrice += item.getPrice();
			total += item.getNumber();
		}
		cart.setTotal(total);
		cart.setTotalPrice(totalPrice);
	}

	@Override
	public boolean addGoodsToCart(Long userId, int number, double oneprice, Long GoodsId) {
		CartItem item = new CartItem();
		item.setUserId(userId);
		item.setNumber(number);
		item.setPrice(oneprice * number);
		item.setStatus(0);
		item.setItemId(UUID.randomUUID().toString());
		item.setGoods(new Goods(GoodsId));
		int result = cartDao.addGoodsIntoCart(item);
		return result >= 1;
	}

	@Override
	public boolean purchase(Long userId) {
		Cart cart = getUserCart(userId);
		if (cart.getGoodsList() == null || cart.getGoodsList().size() == 0) {
			return false;
		}
		String orderId =  UUIDUtils.getUUID_16();
		List<CartItem> list = cart.getGoodsList();
		for (int i = 0; i < list.size(); i++) {
			CartItem item = list.get(i);
			ExpenseRecord record = new ExpenseRecord();
			record.setOrderId(orderId);
			record.setAmount(item.getPrice());
			record.setCount(item.getNumber());
			record.setBuyTime(new Date());
			record.setShopId(item.getShop().getShopId());
			record.setUserId(userId);
			record.setGoods(item.getGoods());
			int n = recordDao.insert(record);
			if (n <= 0) {
				return false;
			}
		}
		int res = cartDao.updateStatus(1, userId);
		return res > 0;
	}
	
	@Override
	public CartItem CheckCartItemByGoodsIdAndUserId(Long userId, Long goodsId) {
		int status = 0;
		return cartDao.getCartItemByUserIdAndGoodsId(userId, goodsId, status);
	}

	@Override
	public List<Order> getCustomerOrders(Long userId) {
		List<String> orderIdList = recordDao.getAllOrderId();
		List<Order> orderList = new ArrayList<>();
		for (int i = 0; i < orderIdList.size(); i++) {
			String orderId = orderIdList.get(i);
			List<ExpenseRecord> recordList = recordDao.getRecordByOrderId(orderId, userId);
			Order order = new Order();
			order.setRecordList(recordList);
			order.setOrderId(orderId);
			order.setOrderTime(recordList.get(0).getBuyTime());
			setAmount(order);
			orderList.add(order);
		}
		return orderList;
	}

	private void setAmount(Order order) {
		List<ExpenseRecord> recordList = order.getRecordList();
		double amount = 0.0;
		int count = 0;
		for(ExpenseRecord e : recordList) {
			amount += e.getAmount();
			count += e.getCount();
		}
		order.setCount(count);
		order.setAmount(amount);
	}

	@Override
	public List<ExpenseRecord> getShopOrders(Long shopId) {
		List<ExpenseRecord> orderList = recordDao.getRecordByShopId(shopId);
		return orderList;
	}
}
