package com.pre.zlm.o2o.dao;

import com.pre.zlm.o2o.entity.User;

public interface UserDao {
	
	/**
	 *	通过id查询用户
	 */
	User getUserById(long userId);
	
	/**
	 * 	添加用户
	 */
	int insertUser(User user);
}
