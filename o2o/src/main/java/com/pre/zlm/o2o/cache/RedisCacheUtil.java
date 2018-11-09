package com.pre.zlm.o2o.cache;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	//默认过期时间一天
	public static final long DEFAULT_EXPIRE = 60 * 60 * 24;
	
	//不设置过期时间,也就是永久
	public static final long NOT_EXPIRE = -1;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	
	@Autowired
	private RedisTemplate<String, Object> rt;
	
	//检查是否存在缓存,即某key是否存在
	public boolean isCacheExist(String key) {
		return rt.opsForHash().hasKey(key, key);
	}
	
	//重命名key,如果存在就覆盖
	public void renameKey(String oldKey, String newKey) {
		rt.rename(oldKey, newKey);
	}
	
	/**
	 * 根据key删除缓存
	 */
	public void deleteByKey(String key) {
		rt.delete(key);
	}
	
	/**
	 * 	删除多个key
	 */
	public void deleteKey(String... keys) {
		Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
		rt.delete(kSet);
	}
	
	/**
	 * 	设置key的生命周期
	 */
	public void expireKey(String key, long timeout, TimeUnit timeUnit) {
		rt.expire(key, timeout, timeUnit);
	}
	
	/**
	 * 指定key在指定的日期过期
	 */
	public void expireKeyAt(String key, Date date) {
		rt.expireAt(key, date);
	}
	
	/**
	 * 查询key的生命周期
	 */
	public long getKeyExpire(String key, TimeUnit timeUnit) {
		return rt.getExpire(key, timeUnit);
	}
	
	/**
	 * 将key设置为永久有效
	 */
	public void persistKey(String key) {
		rt.persist(key);
	}
	
	//添加String类型的key
	public void putInCache(Object object, String key) throws JsonProcessingException {
		Map<String, String> map = new HashMap<>();
		String objStr = objectMapper.writeValueAsString(object);
		map.put(key, objStr);
		rt.opsForHash().putAll(key, map);
		//默认key有效期为一天
		rt.expire(key, 24, TimeUnit.HOURS);
	}
	
	public Object getCache(String key, Class<?> parametrized, Class<?> parameterClasses) 
			throws JsonParseException, JsonMappingException, IOException {
		String objStr = rt.opsForHash().entries(key).get(key).toString();
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
		Object object = objectMapper.readValue(objStr, javaType);
		return object;
	}
	
}
