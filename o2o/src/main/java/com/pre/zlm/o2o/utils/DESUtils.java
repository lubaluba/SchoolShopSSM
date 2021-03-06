package com.pre.zlm.o2o.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
@SuppressWarnings("restriction")
public class DESUtils {

	private static Key key;
	//设置密匙key
	private static String KEY_STR = "myKey";
	private static String CHARSETNAME = "UTF-8";
	private static String ALGORITHM = "DES";

	static {
		try {
			//生成DES算法对象
			KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			//运用SHA1安全策略
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			//设置密匙的种子
			secureRandom.setSeed(KEY_STR.getBytes());
			//初始化基于SHA1的算法对象
			generator.init(secureRandom);
			//生成密匙对象
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 	获取加密后的信息
	 */
	public static String getEncryptString(String str) {
		//基于BASE64编码,接收byte[]并转换为string
		BASE64Encoder base64encoder = new BASE64Encoder();
		try {
			//按UTF8编码
			byte[] bytes = str.getBytes(CHARSETNAME);
			//获取加密对象
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			//初始化加密信息
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//加密
			byte[] doFinal = cipher.doFinal(bytes);
			return base64encoder.encode(doFinal);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}


	/**
	 * 	获取解密后的信息
	 */
	public static String getDecryptString(String str) {
		BASE64Decoder base64decoder = new BASE64Decoder();
		try {
			byte[] bytes = base64decoder.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] doFinal = cipher.doFinal(bytes);
			return new String(doFinal, CHARSETNAME);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getEncryptString("root"));
		System.out.println(getDecryptString("WnplV/ietfQ="));
	}

}