package com.pre.zlm.o2o.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pre.zlm.o2o.dao.UserDao;
import com.pre.zlm.o2o.dao.WechatAuthDao;
import com.pre.zlm.o2o.dto.WechatAuthExecution;
import com.pre.zlm.o2o.entity.User;
import com.pre.zlm.o2o.entity.WechatAuth;
import com.pre.zlm.o2o.enums.WechatAuthEnum;
import com.pre.zlm.o2o.exception.WechatAuthException;
import com.pre.zlm.o2o.service.WechatAuthService;

@Service
public class WechatAuthServiceImpl implements WechatAuthService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WechatAuthDao wechatAuthDao;
	@Autowired
	private UserDao userDao;
	
	@Override
	public WechatAuth getWechatAuthByOpenId(String openId) {
		return wechatAuthDao.getWechatAuthById(openId);
	}

	@Override
	@Transactional
	public WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthException {
		if (wechatAuth == null || wechatAuth.getOpenId() == null) {
			return new WechatAuthExecution(WechatAuthEnum.EMPTY_PARAM);
		}
		try {
			wechatAuth.setCreateTime(new Date());
			//如果微信账号里夹带用户信息且用户id为空,则认为用户第一次使用平台(且通过微信登录)
			if (wechatAuth.getUser() != null && wechatAuth.getUser().getUserId() == null) {
				wechatAuth.getUser().setCreateTime(wechatAuth.getCreateTime());
				wechatAuth.getUser().setLastEditTime(wechatAuth.getCreateTime());
				wechatAuth.getUser().setEnableStatus(1);
				User user = wechatAuth.getUser();
				int effectedNum = userDao.insertUser(user);
				if (effectedNum <= 0) {
					throw new WechatAuthException("添加用户信息失败");
				}
			}
		} catch (Exception e) {
			logger.error("insert user error" + e.toString());
			throw new WechatAuthException("insert user error" + e.getMessage());
		}
		//创建微信账号
		int effectNum = wechatAuthDao.insertWechatAuth(wechatAuth);
		if (effectNum <= 0) {
			throw new WechatAuthException("账号添加失败");
		} else {
			return new WechatAuthExecution(WechatAuthEnum.SUCCESS, wechatAuth);
		}
	}


	
}
