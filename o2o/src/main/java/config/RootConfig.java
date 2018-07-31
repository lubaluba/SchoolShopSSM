package config;
import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Configuration
@ComponentScan(basePackages= {"com.pre.zlm.o2o"})
@MapperScan(basePackages= {"com.pre.zlm.o2o.dao"})
@EnableTransactionManagement
public class RootConfig extends org.apache.ibatis.session.Configuration{
	protected boolean mapUnderscoreToCamelCase=true;
	@Bean
	public BasicDataSource getDataSource(){
		BasicDataSource dataSource =new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/o2o?useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setMaxTotal(10);
		dataSource.setMaxIdle(5);
		return dataSource;
	}
	@Bean
	public SqlSessionFactoryBean getSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(getDataSource());
		sqlSessionFactoryBean.setConfiguration(config());
		/*sqlSessionFactoryBean.setTypeAliasesPackage("cn.pre.zlm.ssm.pojo");*/
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
		return sqlSessionFactoryBean;
	}
	@Bean 
	public DataSourceTransactionManager dataSourceTransactionManager() {
		 DataSourceTransactionManager dataSourceTransactionManager=new DataSourceTransactionManager(getDataSource());
		 return dataSourceTransactionManager;
	}
	public org.apache.ibatis.session.Configuration config(){
		org.apache.ibatis.session.Configuration config =new org.apache.ibatis.session.Configuration();
		config.setMapUnderscoreToCamelCase(true);
		//使用JDBC获得数据库自增的主键值
		config.setUseGeneratedKeys(true);
		return config;
	}

}
