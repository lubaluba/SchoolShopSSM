package com.pre.zlm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pre.zlm.o2o.entity.HeadLine;

public interface HeadLineDao {
	
	/**
	 * 	根据传入的查询条件(根据头条状态查询头条)
	 */
	List<HeadLine> getHeadLineList(@Param("headLineCondition") HeadLine headLineCondition);
	
	/**
	 *	根据ID查询头条
	 */
	HeadLine getHeadLineById(Long lineId);
}
