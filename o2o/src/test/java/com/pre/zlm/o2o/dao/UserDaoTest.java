package com.pre.zlm.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.User;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest extends BaseTest { 
	@Autowired
	UserDao dao;
	
	@Test
	@Ignore
	public void testAInsertUser() {
		User user = new User();
		user.setCreateTime(new Date());
		user.setLastEditTime(new Date());
		user.setName("老二");
		user.setGender("男");
		user.setEmail("sss");
		user.setProfileImg("ss");
		user.setEnableStatus(1);
		user.setUserType(1);
		int res = dao.insertUser(user);
		assertEquals(res, 1);
	}
	
	@Test
	public void testBGetUser() {
		User user = dao.getUserById(2L);
		assertEquals(user.getName(), "老二");
	}
}
