package com.pre.zlm.o2o.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.dto.WechatAuthExecution;
import com.pre.zlm.o2o.entity.User;
import com.pre.zlm.o2o.entity.WechatAuth;
import com.pre.zlm.o2o.enums.WechatAuthEnum;

public class WechatAuthServiceTest extends BaseTest {
	
	@Autowired
	WechatAuthService service;
	
	@Test
	public void testRegister() {
		WechatAuth wa = new WechatAuth();
		wa.setOpenId("zzzz11122");
		User user = new User();
		user.setEmail("a3325299@qq.com");
		user.setGender("男");
		user.setName("老张");
		user.setProfileImg("ssss");
		user.setUserType(2);
		wa.setUser(user);
		WechatAuthExecution wae = service.register(wa);
		assertEquals(wae.getState(), WechatAuthEnum.SUCCESS.getState());
	}
}
