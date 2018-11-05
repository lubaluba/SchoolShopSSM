package com.pre.zlm.o2o.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pre.zlm.o2o.dao.UserDao;
import com.pre.zlm.o2o.entity.User;
import com.pre.zlm.o2o.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao dao;
	
	@Override
	public User getUserById(Long userId) {
		return dao.getUserById(userId);
	}
	
}
