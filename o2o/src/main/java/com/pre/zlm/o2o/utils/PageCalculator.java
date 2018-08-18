package com.pre.zlm.o2o.utils;

public class PageCalculator {

	/** 
	 * @param pageIndex 前端查询的页码,也就是当前页
	 * @param pageSize	前端每页展示条数
	 * @return	将前端页面展示的数目,转换为数据库查询是limit的条数
	 */
	public static int calculateRowIndex(int pageIndex, int pageSize) {
		return (pageIndex > 0) ? (pageIndex - 1)*pageSize : 0;
	}
}
