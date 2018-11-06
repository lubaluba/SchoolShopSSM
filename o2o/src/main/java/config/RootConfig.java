package config;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.pre.zlm.o2o.dao.interceptor.DynamicDataSourceInterceptor;
import com.pre.zlm.o2o.dao.split.DynamicDataSource;
import com.pre.zlm.o2o.utils.DESUtils;
@Configuration
@ComponentScan(basePackages= {"com.pre.zlm.o2o"})
@MapperScan(basePackages= {"com.pre.zlm.o2o.dao"})
@EnableTransactionManagement
@PropertySource("classpath:jdbc.properties")
public class RootConfig extends org.apache.ibatis.session.Configuration{
	protected boolean mapUnderscoreToCamelCase=true;

	@Value("${datasource.driverClassName}")
	private String driverClassName;
	
	@Value("${datasource.master.url}")
	private String masterURL;
	
	@Value("${datasource.slave.url}")
	private String slaveURL;
	
	@Value("${datasource.username}")
	private String userName;
	
	@Value("${datasource.password}")
	private String password;
	
	@Value("${datasource.maxtotal}")
	private int maxTotal;
	
	@Value("${datasource.maxidle}")
	private int maxIdle;
	/**
	 * 配置动态数据源,这里面的targetDataSources就是路由数据源对应的名称
	 */
	@Bean
	public DynamicDataSource  getDynamicDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		Map<Object, Object> map = new HashMap<>();
		map.put("master", getMasterDataSource());
		map.put("slave", getSlaveDataSource());
		dynamicDataSource.setTargetDataSources(map);
		return dynamicDataSource;
	}
	
	/**
	 * 懒加载dataSource,这样就可以根据具体的操作去使用不同的数据库
	 */
	@Bean
	public LazyConnectionDataSourceProxy getDataSource() {
		LazyConnectionDataSourceProxy dataSource = new LazyConnectionDataSourceProxy(getDynamicDataSource());
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactoryBean getSqlSessionFactory() throws Exception {
		
		SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
		
		//配置数据源
		sqlSessionFactoryBean.setDataSource(getDataSource());
		
		//配置mybatis拦截的第二种方式,但是觉写在config中更方便
		/*
		 *Interceptor[] interceptors = new Interceptor[1];
		 *interceptors[0] = new DynamicDataSourceInterceptor();
		 *sqlSessionFactoryBean.setPlugins(interceptors);
		*/
		//加载mybatis全局配置
		sqlSessionFactoryBean.setConfiguration(config());
		/*sqlSessionFactoryBean.setTypeAliasesPackage("cn.pre.zlm.ssm.pojo");*/
		
		//mapper地址
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
		
		return sqlSessionFactoryBean;
	}
	
	@Bean 
	public DataSourceTransactionManager dataSourceTransactionManager() {
		
		 DataSourceTransactionManager dataSourceTransactionManager=new DataSourceTransactionManager(getDataSource());
		 
		 return dataSourceTransactionManager;
	}
	
	/**
	 * mybatis全局属性配置,相当于mybatis-config中的<Configuration>
	 */
	public org.apache.ibatis.session.Configuration config(){
		
		org.apache.ibatis.session.Configuration config =new org.apache.ibatis.session.Configuration();
		
		/**
		 * 开启驼峰式命名匹配,如:Table{create_time} --> Entity{createTime}
		 */
		config.setMapUnderscoreToCamelCase(true);
		
		/**
		 * 使用JDBC获得数据库自增的主键值
		 */
		config.setUseGeneratedKeys(true);
		
		/**
		 * 配置好的interceptor
		 */
		config.addInterceptor(new DynamicDataSourceInterceptor());
		
		return config;
	}
	
	
	/**
	 * 主数据库
	 */
	public BasicDataSource getMasterDataSource(){
		BasicDataSource dataSource =new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(masterURL);
		dataSource.setUsername(DESUtils.getDecryptString(userName));
		dataSource.setPassword(DESUtils.getDecryptString(password));
		dataSource.setMaxIdle(maxIdle);
		dataSource.setMaxTotal(maxTotal);
		return dataSource;
	}
	
	/**
	 * 从数据库
	 */
	public BasicDataSource getSlaveDataSource() {
		BasicDataSource dataSource =new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(slaveURL);
		dataSource.setUsername(DESUtils.getDecryptString(userName));
		dataSource.setPassword(DESUtils.getDecryptString(password));
		dataSource.setMaxIdle(maxIdle);
		dataSource.setMaxTotal(maxTotal);
		return dataSource;
	}
	
}
