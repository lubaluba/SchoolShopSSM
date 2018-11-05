package com.pre.zlm.o2o.service;

import com.pre.zlm.o2o.dto.WechatAuthExecution;
import com.pre.zlm.o2o.entity.WechatAuth;
import com.pre.zlm.o2o.exception.WechatAuthException;

public interface WechatAuthService {
	/**
	 * 	通过openId查找平台对应的微信号
	 */
	WechatAuth getWechatAuthByOpenId(String openId);
	
	/**
	 *	注册本平台的微信账号
	 */
	WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthException;
}
