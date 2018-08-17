package com.pre.zlm.o2o.dao.interceptor;

import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.pre.zlm.o2o.dao.split.DynamicDataSourceHolder;
/**
 * mybatis拦截器,拦截sql请求,如果是insert或者update就走master数据库
 * 如果是query请求就走slave数据库
 * 实现mybatis的拦截器接口
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
,@Signature(type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}, method = "query")})
public class DynamicDataSourceInterceptor implements Interceptor {

	//用于判断sql语句是不是增(insert)删(delete)改(update)的正则表达式
	private static final String REGEX_INSERT = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";
	
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
	
	/**
	 * 拦截方法,根据情况去拦截,这里决定使用哪个数据源
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//判断当前拦截的对象是不是事务,也就是有@Transactional注解的类
		boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
		Object[] objects = invocation.getArgs();
		MappedStatement ms = (MappedStatement)objects[0];
		String lookupKey = DynamicDataSourceHolder.DB_MASTER;
		if ( ! synchronizationActive) {			
			//判断是不是读操作
			if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
				//如果selectKey为自增id查询主键(SELECT LAST_INSERT_ID()),使用主库
				if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
					lookupKey = DynamicDataSourceHolder.DB_MASTER;
				} else {
					BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
					String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
					if (sql.matches(REGEX_INSERT)) {
						lookupKey = DynamicDataSourceHolder.DB_MASTER;
					} else {
						lookupKey = DynamicDataSourceHolder.DB_SLAVE;
					}
				}
			}
		}

		//打印日志[{}]是格式占位符,类似于c语言中的printf
		logger.debug("设置方法[{}] use [{}] Strategy, SqlCommandType [{}]...", ms.getId(), lookupKey, ms.getSqlCommandType().name());
		DynamicDataSourceHolder.setDbType(lookupKey);
		return invocation.proceed();
	}

	/**
	 *	返回封装好的对象(代理对象),拦截器的原理就是在我们需要拦截的类外置一层代理
	 *	该方法决定的就是返回的是本体,还是织入后的代理
	 */
	@Override
	public Object plugin(Object target) {
		//我们拦截的类如果是一个mybatis的Executor,就对其进行拦截。调用warp方法将拦截器注入 
		if (target instanceof Executor) {	//Executor其实就是一个执行CURD操作的类,当拦截器检测到这样的类,就拦截。
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	/**
	 * 类的初始化是设置配置
	 */
	@Override
	public void setProperties(Properties properties) {
		
	}

}
