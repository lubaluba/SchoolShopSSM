package com.pre.zlm.o2o.utils;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };

	/**
	 * 对关键属性进行转换
	 */
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryptProp(propertyName)) {
			//对已加密的字段进行解密工作
			String decryptValue = DESUtils.getDecryptString(propertyValue);
			return decryptValue;
		} else {
			return propertyValue;
		}
	}

	/**
	 * 判断该属性是否已经加密
	 */
	private boolean isEncryptProp(String propertyName) {
		for (String encryptpropertyName : encryptPropNames) {
			if (encryptpropertyName.equals(propertyName))
				return true;
		}
		return false;
	}
}
