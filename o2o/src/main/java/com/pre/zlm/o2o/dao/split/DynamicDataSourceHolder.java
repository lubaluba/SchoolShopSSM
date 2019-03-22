package com.pre.zlm.o2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class DynamicDataSourceHolder {
	
	private static Logger logger  = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
	//保证线程安全
	private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
	
	//master_key
	public static final String DB_MASTER = "master";
	public static final String DB_SLAVE = "slave";
	
	public static String getDbType() {
		String db = contextHolder.get();
		if (db == null) {
			db = DB_MASTER;
		}
		return db;
	} 
	/**
	 *	设置线程的dbType
	 */
	public static void setDbType(String str) {
		logger.debug("所使用的数据源为:" + str);
		contextHolder.set(str);
	}
	/**
	 * 清理数据库连接类型
	 */
	public static void clearDbType() {
		contextHolder.remove();
	}
}
