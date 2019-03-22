package com.pre.zlm.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.ExpenseRecord;
import com.pre.zlm.o2o.entity.Goods;
import com.pre.zlm.o2o.utils.UUIDUtils;

public class ExpenseRecordDaoTest extends BaseTest {

	@Autowired
	ExpenseRecordDao dao;
	
	@Test
	@Ignore
	public void testInsert() {
		ExpenseRecord er = new ExpenseRecord();
		er.setAmount(1000.00);
		er.setBuyTime(new Date());
		er.setCount(10);
		er.setGoods(new Goods(1L));
		er.setUserId(2L);
		er.setOrderId(UUIDUtils.getUUID_32());
		er.setShopId(1L);
		int res = dao.insert(er);
		assertEquals(res, 1);
	}
	
	@Test
	public void testGetOrderId() {
		List<String> list = dao.getAllOrderId();
		assertEquals(list.size(), 2);
	}
	
	@Test
	@Ignore
	public void testGetByUserId() {
		List<ExpenseRecord> list = dao.getRecordByUserId(2L);
		assertEquals(1, list.size());
	}
}
