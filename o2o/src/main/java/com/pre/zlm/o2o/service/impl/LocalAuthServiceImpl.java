package com.pre.zlm.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pre.zlm.o2o.dao.LocalAuthDao;
import com.pre.zlm.o2o.dto.LocalAuthExecution;
import com.pre.zlm.o2o.entity.LocalAuth;
import com.pre.zlm.o2o.enums.LocalAuthEnum;
import com.pre.zlm.o2o.exception.LocalAuthException;
import com.pre.zlm.o2o.service.LocalAuthService;
import com.pre.zlm.o2o.utils.MD5;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {

	@Autowired
	LocalAuthDao localAuthDao;
	
	@Override
	public LocalAuth getLocalAuthByUsernameAndPwd(String username, String password) {
		return localAuthDao.getLocalAuthByUsernameAndPwd(username, MD5.getMd5(password));
	}

	@Override
	public LocalAuth getLocalAuthByUserId(long userId) {
		return localAuthDao.getLocalAuthByUserId(userId);
	}

	@Override
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthException {
		//空值判断,传入的localAuth账号密码,特别是userId不能为空
		if (localAuth == null || localAuth.getPassword() == null || localAuth.getUsername() == null ||
				localAuth.getUser() == null || localAuth.getUser().getUserId() == null) {
			return new LocalAuthExecution(LocalAuthEnum.PARAM_NULL);
		}
		//查询该用户是否已经绑定过平台账户了
		LocalAuth tmpAuth = localAuthDao.getLocalAuthByUserId(localAuth.getUser().getUserId());
		if (tmpAuth != null) {
			//如果已经绑定账号就直接退出,保证平台账号唯一性
			return new LocalAuthExecution(LocalAuthEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			int res = localAuthDao.insertLocalAuth(localAuth);
			if (res != 1) {
				throw new LocalAuthException("账号创建失败");
			} else {
				return new LocalAuthExecution(LocalAuthEnum.SUCCESS, localAuth);
			}
		} catch (Exception e) {
			throw  new LocalAuthException("账号创建失败" + e.getMessage());
		}
	}

	@Override
	@Transactional
	public LocalAuthExecution changePassword(String username, String password, String newPassword, long userId) {
		if (username == null || password == null || newPassword == null || userId <= 0) {
			return new LocalAuthExecution(LocalAuthEnum.PARAM_NULL);
		}
		try {
			LocalAuth localAuth = localAuthDao.getLocalAuthByUsernameAndPwd(username, MD5.getMd5(password));
			if (localAuth != null && localAuth.getUser().getUserId() == userId) {
				localAuth.setPassword(MD5.getMd5(newPassword));
				localAuth.setLastEditTime(new Date());
				int effectNum = localAuthDao.updateLocalAuth(localAuth);
				if (effectNum != 1) {
					return new LocalAuthExecution(LocalAuthEnum.UPDATE_ERROR);
				} 
				return new LocalAuthExecution(LocalAuthEnum.SUCCESS);
			} else {
				return new LocalAuthExecution(LocalAuthEnum.USER_ERROR);
			}
		} catch (Exception e) {
			throw new LocalAuthException("密码更新失败" + e.getMessage());
		}
	}
}
