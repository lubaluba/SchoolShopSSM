package com.pre.zlm.o2o.exception;

public class WechatAuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public WechatAuthException(String msg) {
		super(msg);
	}
}
