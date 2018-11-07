package com.pre.zlm.o2o.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class RedisCacheUtil {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private RedisTemplate<String, Object> rt;
	
	public boolean isCacheExist(String key) {
		return rt.opsForHash().hasKey(key, key);
	}
	
	public void putInCache(Object object, String key) throws JsonProcessingException {
		Map<String, String> map = new HashMap<>();
		String objStr = objectMapper.writeValueAsString(object);
		map.put(key, objStr);
		rt.opsForHash().putAll(key, map);
	}
	
	public Object getCache(String key, Class<?> parametrized, Class<?> parameterClasses) 
			throws JsonParseException, JsonMappingException, IOException {
		String objStr = rt.opsForHash().entries(key).get(key).toString();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
		Object object = objectMapper.readValue(objStr, javaType);
		return object;
	}
	
	/**
	 * 根据key删除缓存
	 * @param key
	 */
	public void deleteByKey(String key) {
		rt.delete(key);
	}
	
}
