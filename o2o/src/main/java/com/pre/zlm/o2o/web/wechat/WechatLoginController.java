package com.pre.zlm.o2o.web.wechat;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pre.zlm.o2o.utils.weixin.WechatUserUtil;
import com.pre.zlm.o2o.utils.weixin.pojo.UserAccessToken;
import com.pre.zlm.o2o.utils.weixin.pojo.WechatUser;

@Controller
@RequestMapping("wechatlogin")
/**
 * 	从微信菜单点击后调用的接口，可以在url里增加参数（role_type）来表明是从商家还是从玩家按钮进来的，依次区分登陆后跳转不同的页面
 * 	玩家会跳转到index.html页面
 * 	商家如果没有注册，会跳转到注册页面，否则跳转到任务管理页面
 * 	如果是商家的授权用户登陆，会跳到授权店铺的任务管理页面
 * 	@author zlm
 *
 */
public class WechatLoginController {

	private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);


	@RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		log.debug("weixin login get...");
		//获取微信公众号传过来的code,通过code可以获取access_token,进而获取用户信息
		String code = request.getParameter("code");
		//String roleType = request.getParameter("state");
		log.debug("weixin login code:" + code);
		WechatUser user = null;
		String openId = null;
		if (null != code) {
			UserAccessToken token;
			try {
				//通过code获取access_token
				token = WechatUserUtil.getUserAccessToken(code);
				log.debug("weixin login token:" + token.toString());
				//通过token获取access_token
				String accessToken = token.getAccessToken();
				//通过token获取openId
				openId = token.getOpenId();
				//通过access_token和openId获取用户昵称信息等
				user = WechatUserUtil.getUserInfo(accessToken, openId);
				log.debug("weixin login user:" + user.toString());
				request.getSession().setAttribute("openId", openId);
			} catch (IOException e) {
				log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
				e.printStackTrace();
			}
		}
		if(user != null) {
			//获取到微信验证的信息后返回到指定的路由(需要自己设定)
			return "frontend/index";
		} else {
			return null;
		}
	}
}

