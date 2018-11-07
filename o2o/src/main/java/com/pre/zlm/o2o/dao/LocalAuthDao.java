package com.pre.zlm.o2o.dao;

import org.apache.ibatis.annotations.Param;

import com.pre.zlm.o2o.entity.LocalAuth;

public interface LocalAuthDao {
	
	/**
	 *	通过账号密码查询对应本地用户信息
	 */
	LocalAuth getLocalAuthByUsernameAndPwd(@Param("username") String username, @Param("password") String password);
	
	/**
	 *	根据用户id查询对应的LocalAuth
	 */
	LocalAuth getLocalAuthByUserId(long userId);
	
	/**
	 *	添加平台账号
	 */
	int insertLocalAuth(LocalAuth localAuth);
	
	/**
	 * 修改账号信息,主要是账号密码
	 */
	int updateLocalAuth(LocalAuth localAuth);
}
