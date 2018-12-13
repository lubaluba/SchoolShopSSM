package com.pre.zlm.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pre.zlm.o2o.entity.UserAwardMap;

public interface UserAwardMapDao {
	/**
	 * 分页查询顾客已领取奖品
	 * @param userAwardCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<UserAwardMap> queryUserAwardMapList(
			@Param("userAwardCondition") UserAwardMap userAwardCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	/**
	 * 查询总数
	 * @param userAwardCondition
	 * @return
	 */
	int queryUserAwardMapCount(
			@Param("userAwardCondition") UserAwardMap userAwardCondition);

	/**
	 * 根据id查询
	 * @param userAwardId
	 * @return
	 */
	UserAwardMap queryUserAwardMapById(long userAwardId);

	/**
	 * 插入
	 * @param userAwardMap
	 * @return
	 */
	int insertUserAwardMap(UserAwardMap userAwardMap);

	/**
	 * 更新
	 * @param userAwardMap
	 * @return
	 */
	int updateUserAwardMap(UserAwardMap userAwardMap);
}
