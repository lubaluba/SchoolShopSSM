package com.pre.zlm.o2o.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.LocalAuth;

public class LocalAuthDaoTest extends BaseTest{
	
	@Autowired
	LocalAuthDao dao;
	
	@Autowired
	UserDao userDao;
	
	@Test
	@Ignore
	public void testInsertLocalAuth() {
		LocalAuth la = new LocalAuth();
		la.setUsername("test");
		la.setPassword("test");
		la.setCreateTime(new Date());
		la.setLastEditTime(new Date());
		la.setUser(userDao.getUserById(1L));
		int effectNum = dao.insertLocalAuth(la);
		assertEquals(1, effectNum);
	}
	
	@Test
	public void testGetLocalAuthByUsernameAndPwd() {
		LocalAuth la = dao.getLocalAuthByUsernameAndPwd("test", "test");
		assertNotNull(la);
		assertNotNull(la.getUser());
		assertEquals(la.getUser().getName(), "王小二");
	}
	
	@Test
	public void testGetLocalAuthByUserId() {
		LocalAuth la = dao.getLocalAuthByUserId(1L);
		assertNotNull(la);
		assertNotNull(la.getUser());
		assertEquals(la.getUser().getName(), "王小二");
	}
	
	@Test
	public void tsetUpdateLocalAuth() {
		LocalAuth la = dao.getLocalAuthByUserId(1L);
		la.setPassword("test1");
		la.setLastEditTime(new Date());
		int effectNum = dao.updateLocalAuth(la);
		assertEquals(effectNum, 1);
	}
}
