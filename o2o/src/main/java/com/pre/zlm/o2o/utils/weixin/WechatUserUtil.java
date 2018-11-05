package com.pre.zlm.o2o.utils.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pre.zlm.o2o.entity.User;
import com.pre.zlm.o2o.utils.weixin.pojo.UserAccessToken;
import com.pre.zlm.o2o.utils.weixin.pojo.WechatUser;

/**
 * 	@author zlm
 * 	主要用来提交https请求给微信获取用户信息
 */
public class WechatUserUtil {

	private static Logger log = LoggerFactory.getLogger(WechatUserUtil.class);

	public  UserAccessToken getUserAccessToken(String code) throws IOException {
		//测试号信息里的appId和appsecret
		String appId = "wx1fe36a53f8f2ee1d";
		log.debug("appId:" + appId);
		String appsecret = "2bec863f37baa81ef870eefaf0f8f61e";
		log.debug("secret:" + appsecret);
		//根据传入的code,拼接出访问微信定义好的接口url
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appId + "&secret=" + appsecret + "&code=" + code
				+ "&grant_type=authorization_code";
		//向相应的URL发送请求获取token json字符串
		String tokenStr = httpsRequest(url, "GET", null);
		log.debug("userAccessToken:" + tokenStr);
		UserAccessToken token = new UserAccessToken();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			token = objectMapper.readValue(tokenStr, UserAccessToken.class);
		} catch (Exception e) {
			log.error("获取用户access Token失败", e.getMessage());
			e.printStackTrace();
		}
		if (token == null) {
			log.error("获取用户access Token失败");
			return null;
		}
		return token;
	}

	/**
	 * 	发起https请求并获取结果
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			//创建SSLContext对象,并使用我们指定信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			
			URL url = new URL(requestUrl);
			HttpsURLConnection httpsUrlConnection = (HttpsURLConnection)url.openConnection();
			httpsUrlConnection.setSSLSocketFactory(ssf);
			
			httpsUrlConnection.setDoOutput(true);
			httpsUrlConnection.setDoInput(true);
			httpsUrlConnection.setUseCaches(false);
			httpsUrlConnection.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpsUrlConnection.connect();
			}
			
			if(null != outputStr) {
				OutputStream outputStream = httpsUrlConnection.getOutputStream();
				//注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = httpsUrlConnection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			httpsUrlConnection.disconnect();
			log.debug("https buffer:" + buffer.toString());
		} catch (ConnectException ce) {
			log.error("微信服务连接超时");
		} catch (Exception e) {
			log.error("https request error:{}");
		}	
		return buffer.toString();
	}
	

	/**
	 *	将WechatUser里的信息转换成User的信息并返回
	 */
	public static User getUserFromRequest(WechatUser wechatUser) {
		User user = new User();
		user.setName(wechatUser.getNickName());
		user.setGender(wechatUser.getSex() == 0 ? "男" : "女" );
		user.setProfileImg(wechatUser.getHeadimgurl());
		user.setEnableStatus(1);
		return user;
	}
	
	public static WechatUser getUserInfo(String accessToken, String openId) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ accessToken + "&openid=" + openId + "&lang=zh_CN";
		String jsonObject = httpsRequest(url, "GET", null);
		ObjectMapper mapper = new ObjectMapper(); 
		WechatUser user = new WechatUser();
		try {
			user = mapper.readValue(jsonObject, WechatUser.class);
		} catch (IOException e) {
			log.error("获取用户信息失败");
			e.printStackTrace();
		}
		return user;
	}
}
