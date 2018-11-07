package com.pre.zlm.o2o.service;

import com.pre.zlm.o2o.dto.LocalAuthExecution;
import com.pre.zlm.o2o.entity.LocalAuth;
import com.pre.zlm.o2o.exception.LocalAuthException;

public interface LocalAuthService {
	
	LocalAuth getLocalAuthByUsernameAndPwd(String username, String password);
	
	LocalAuth getLocalAuthByUserId(long userId);
	
	LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthException;
	
	LocalAuthExecution changePassword(String username, String password, String newPassword, long userId);
}
