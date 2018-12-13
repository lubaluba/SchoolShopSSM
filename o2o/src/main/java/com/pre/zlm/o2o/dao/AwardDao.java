package com.pre.zlm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pre.zlm.o2o.entity.Award;

public interface AwardDao {

	/**
	 * 根据传入的查询条件分页显示奖品信息列表
	 */
	List<AwardDao> listAwardDaoByPage(@Param("awardCondition") Award awardCondition, @Param("rowIndex") int rowIndex, 
			@Param("pageSize") int pageSize);

	/**
	 * 获取条件查询时的总数
	 */
	int getAwardCount(@Param("awardCondition")Award awardCondition);
	
	/**
	 * 通过awardId查询奖品信息
	 */
	Award getAwardById(Long awardId);
	
	/**
	 * 添加奖品信息
	 */
	int insertAward(Award award);
	
	/**
	 * 更新奖品信息
	 */
	int updateAward(Award award);
	
	/**
	 * 删除奖品信息
	 */
	int deleteAward(@Param("awardId") long awardId, @Param("shopId") long shopId);
}
