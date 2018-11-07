package com.pre.zlm.o2o.service;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pre.zlm.o2o.BaseTest;
import com.pre.zlm.o2o.entity.Area;
import com.pre.zlm.o2o.entity.User;
public class AreaServiceTest extends BaseTest{
	@Autowired
	AreaService service;
	
	@Autowired
	RedisTemplate<String, Object>  rt; 
	
	@Test
	@Ignore
	public void testRedis() throws IOException {
		User user1 = new User();
		user1.setName("张三");
		User user2 = new User();
		user2.setName("李四");
		User user = new User();
		user.setName("王二");
		List<User> list = new ArrayList<>();
		list.add(user);
		list.add(user2);
		list.add(user1);
		ObjectMapper om = new ObjectMapper();
		String str = om.writeValueAsString(list);
		Map<String, String> map = new HashMap<>();
		map.put("userlist", str);
		rt.opsForHash().putAll("userlist", map);
		JavaType javaType = om.getTypeFactory().constructParametricType(ArrayList.class, User.class);
		String userstr = rt.opsForHash().entries("userlist").get("userlist").toString();
		List<User> userList = om.readValue(userstr, javaType);
		assertEquals(3, userList.size());
		System.out.println(userList);
	}
	
	@Test
	public void test() {
		List<Area> list =service.getAreaList();
		assertEquals("科教新村", list.get(0).getAreaName());
	}
	
}
