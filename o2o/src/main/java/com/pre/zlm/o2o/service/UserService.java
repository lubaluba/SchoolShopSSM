package com.pre.zlm.o2o.service;

import com.pre.zlm.o2o.entity.User;

public interface UserService {
	
	/**
	 *	根据用户id获取用户信息
	 */
	User getUserById(Long userId);
}
