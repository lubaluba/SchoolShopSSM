package com.pre.zlm.o2o.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

/**
 *
 */
public class JWTUtils {
	
	//设置过期时间,目前是15分钟
	private static final long EXPIRE_TIME = 15 * 60 * 1000;
	
	//token私钥,加密和解密需要用到
	private static final String SECRET = "session_secret";
	
	private static final String ISSUE = "zlm";
	
	/**
	 * 	生成token,将需要的参数放置token中
	 * 	@param claims
	 * 	@return
	 */
	public static String getToken(Map<String, String> claims) {
		try {
			//私钥及加密算法
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			//过期时间
			Date date  = new Date(System.currentTimeMillis()+EXPIRE_TIME);
			JWTCreator.Builder builder = JWT.create().withIssuer(ISSUE).withExpiresAt(date);
			claims.forEach((k, v) -> builder.withClaim(k, v));
			return builder.sign(algorithm).toString(); 
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * 	校验token
	 * 	@param token
	 * 	@return
	 */
	public static Map<String, String> verifyToken(String token) {
		Algorithm algorithm = null;
		try {
			algorithm = Algorithm.HMAC256(SECRET);
			JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUE).build();
			DecodedJWT jwt = verifier.verify(token);
			Map<String, Claim> map = jwt.getClaims();
			Map<String, String> resultMap = new HashMap<String, String>();
			map.forEach((k, v) -> resultMap.put(k, v.toString()));
			return resultMap;
		} catch (Exception e) {
			throw new RuntimeException();
		}	
	}
}
