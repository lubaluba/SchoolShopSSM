package com.pre.zlm.o2o.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.User;
import com.pre.zlm.o2o.entity.WechatAuth;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WechatAuthDaoTest extends BaseTest {
	
	@Autowired
	WechatAuthDao dao;
	
	@Autowired
	UserDao userDao;
	
	@Test
	public void testAInsertWechatAuth() {
		User user = userDao.getUserById(2L);
		WechatAuth we = new WechatAuth();
		we.setUser(user);
		we.setCreateTime(new Date());
		we.setOpenId("sh");
		int res = dao.insertWechatAuth(we);
		assertEquals(res, 1);
	}
	
	@Test
	public void testBGetWechatAuthById() {
		WechatAuth we = dao.getWechatAuthById("ss");
		assertNotNull(we.getUser());
	}
}
