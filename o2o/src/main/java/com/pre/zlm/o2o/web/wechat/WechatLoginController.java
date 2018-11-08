package com.pre.zlm.o2o.web.wechat;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pre.zlm.o2o.dto.WechatAuthExecution;
import com.pre.zlm.o2o.entity.User;
import com.pre.zlm.o2o.entity.WechatAuth;
import com.pre.zlm.o2o.enums.WechatAuthEnum;
import com.pre.zlm.o2o.service.UserService;
import com.pre.zlm.o2o.service.WechatAuthService;
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
	private static final String FRONTEND = "1";
	private static final String SHOPEND = "2";

	@Autowired
	private UserService userService;
	@Autowired
	private WechatAuthService wechatAuthService;
	
	
	@RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		log.debug("weixin login get...");
		//获取微信公众号传过来的code,通过code可以获取access_token,进而获取用户信息
		String code = request.getParameter("code");
		String roleType = request.getParameter("state");
		log.debug("weixin login code:" + code);
		WechatUser wechatUser = null;
		String openId = null;
		WechatAuth auth = null;
		if (null != code) {
			UserAccessToken token;
			try {
				//通过code获取access_token
				token = new WechatUserUtil().getUserAccessToken(code);
				log.debug("weixin login token:" + token.toString());
				//通过token获取access_token
				String accessToken = token.getAccessToken();
				//通过token获取openId
				openId = token.getOpenId();
				//通过access_token和openId获取用户昵称信息等
				wechatUser = WechatUserUtil.getUserInfo(accessToken, openId);
				log.debug("weixin login user:" + wechatUser.toString());
				request.getSession().setAttribute("openId", openId);
				auth = wechatAuthService.getWechatAuthByOpenId(openId);
			} catch (IOException e) {
				log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
				e.printStackTrace();
			}
		}
		//前面获取了openId后,可以通过它去数据库判断微信账号是否在我们网站里面有对应账号了
		//如果没有该微信账号,可以自动创建上,直接实现微信与网站的无缝对接。
		if(auth == null) {
			User user = WechatUserUtil.getUserFromRequest(wechatUser);
			auth = new WechatAuth();
			auth.setOpenId(openId);
			//这里主要是看进入的是前端展示页面还是后台管理页面,如果前端展示页面,那么就是顾客,否则就是店家。
			if (FRONTEND.equals(roleType)) {
				user.setUserType(1);
			} else {
				user.setUserType(2);
			}
			auth.setUser(user);
			WechatAuthExecution we = wechatAuthService.register(auth);
			if (we.getState() != WechatAuthEnum.SUCCESS.getState()) {
				return null;
			} else {
				user = userService.getUserById(auth.getUser().getUserId());
				request.getSession().setAttribute("user", user);
			}
		}
		//根据roleType决定是跳转到店铺前端展示页面还是后台管理页
		if (SHOPEND.equals(roleType)) {
			return "o2o/shop/shoplist.jsp";
		} else {
			return "o2o/frontend/index.jsp";
		}
	}
}

