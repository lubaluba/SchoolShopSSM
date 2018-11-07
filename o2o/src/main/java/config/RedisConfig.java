package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
@PropertySource("classpath:redis.properties")
public class RedisConfig {
	
	@Value("${redis.url}")
	private String host;
	
	@Value("${redis.port}")
	private int port;
	
	@Value("${redis.timeout}")
	private String timeout;
	
	@Value("${redis.password}")
	private String password;
	
	@Value("${redis.pool.maxTotal}")
	private int maxTotal;
	
	@Value("${redis.pool.minIdle}")
	private int maxIdle;
	
	@Value("${redis.pool.maxWait}")
	private int maxWait;
	
	@Value("${redis.pool.testOnBorrow}")
	private boolean testOnBorrow;
	
	@Value("${redis.pool.testOnReturn}")
	private boolean testOnReturn;
	
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		//一个pool可分配多少个jedis实例
		jedisPoolConfig.setMaxTotal(maxTotal);	
		//最大空闲数
		jedisPoolConfig.setMaxIdle(maxIdle);
		//最大建立连接等待时间。如果超过此时间将接到异常,设为-1表示无限制。
		jedisPoolConfig.setMaxWaitMillis(maxWait);
		//是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		jedisPoolConfig.setTestOnReturn(testOnReturn);
		return jedisPoolConfig;
	}

	@Bean
	 public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(host);	//ip
		configuration.setPort(port);		//端口
		configuration.setPassword(password);//密码
	    //获得默认的连接池构造
	    //JedisConnectionFactory对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数，
	    //所以用JedisClientConfiguration接口的builder方法实例化一个构造器，还得类型转换
	    JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf =
	    		(JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
	    //修改我们的连接池配置
	    jpcf.poolConfig(jedisPoolConfig());
	    //通过构造器来构造jedis客户端配置
	    JedisClientConfiguration jedisClientConfiguration = jpcf.build();
	    return new JedisConnectionFactory(configuration, jedisClientConfiguration);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		//序列化
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
		return redisTemplate;
	}
	
	@Bean
	public RedisCacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory) {
		//这里是个坑，老方法已经过时了，用下面方法替代
		return RedisCacheManager.create(jedisConnectionFactory);
	}
}
