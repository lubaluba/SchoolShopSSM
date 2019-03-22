package com.pre.zlm.o2o.web.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pre.zlm.o2o.cache.RedisCacheUtil;
import com.pre.zlm.o2o.dto.LocalAuthExecution;
import com.pre.zlm.o2o.entity.LocalAuth;
import com.pre.zlm.o2o.entity.User;
import com.pre.zlm.o2o.enums.LocalAuthEnum;
import com.pre.zlm.o2o.service.LocalAuthService;
import com.pre.zlm.o2o.utils.HttpServletRequestUtils;
import com.pre.zlm.o2o.web.BaseController;

@Controller
@RequestMapping(value = "/local", method = {RequestMethod.GET, RequestMethod.POST})
public class LocalAuthController extends BaseController {

	@Autowired
	private LocalAuthService service;
	
	@Autowired
	RedisCacheUtil redis;
	
	RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
	//登录验证
	@ResponseBody
	@RequestMapping(value = "/logincheck", method = {RequestMethod.POST})
	public Map<String, Object> loginCheck(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean needVerify = HttpServletRequestUtils.getBoolean(request, "needVerify");
		if( needVerify && ! checkCode()) {
			return exceptionResult(result, "验证码错误");
		}
		String username = HttpServletRequestUtils.getString(request, "username");
		String password = HttpServletRequestUtils.getString(request, "password");
		if (username != null && password != null) {
			LocalAuth localAuth = service.getLocalAuthByUsernameAndPwd(username, password);			
			if (localAuth != null) {
				//拿到登录的user后生成token
				//onLogin(localAuth);
				result.put("success", true);
				result.put("userType", localAuth.getUser().getUserType());
				request.getSession().setAttribute("user", localAuth.getUser());
			} else {
				return exceptionResult(result, "用户名或密码错误");
			}
		} else {
			return exceptionResult(result, "用户名或密码为空");
		}
		return result;
	}
	
	//鉴权接口
	@ResponseBody
	@RequestMapping(value = "/auth", method = {RequestMethod.POST})
	public Map<String, Object> getUser(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			result.put("success", true);
			result.put("user", user);
		} else {
			result.put("success", false);
		}
		return result;
	}
	
	//根据token获取User
	/*
	 * public LocalAuth getLoginedUserByToken(String token) { Map<String, String>
	 * map = null; try { map = JWTUtils.verifyToken(token); } catch (Exception e) {
	 * throw new LocalAuthException("请检查登录状态"); } String username =
	 * map.get("username"); String password = map.get("password"); LocalAuth user =
	 * service.getLocalAuthByUsernameAndPwd(username, password);
	 * user.setToken(token); return user; }
	 */
	
	//绑定本地账户(即一个user关联了一个本地账户(localAuth)和一个微信账户(wechatAuth))
	@ResponseBody
	@RequestMapping(value = "/bindlocalauth", method = {RequestMethod.POST})
	public Map<String, Object> registerLocalAuth(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (! checkCode()) {
			return exceptionResult(result, "验证码错误");
		}
		String username = HttpServletRequestUtils.getString(request, "username");
		String password = HttpServletRequestUtils.getString(request, "password");
		//从session获取user
		User user = (User)request.getSession().getAttribute("user");
		if (username == null || password == null || user == null || user.getUserId() <= 0) {
			return exceptionResult(result, "传入参数有误,请检查参数");
		}
		LocalAuth localAuth = new LocalAuth();
		localAuth.setUsername(username);
		localAuth.setPassword(password);
		localAuth.setUser(user);
		LocalAuthExecution lae = service.bindLocalAuth(localAuth);
		if(lae.getState() == LocalAuthEnum.SUCCESS.getState()) {
			result.put("success", true);
		} else {
			return exceptionResult(result, lae.getStateInfo());
		}
		return result;
	}
	
	//修改用户密码
	@ResponseBody
	@RequestMapping(value = "/changepwd", method = RequestMethod.POST) 
	public Map<String, Object> changeLocalAuthPassword(HttpServletRequest request){
		Map<String, Object> result = new HashMap<>();
		if (! checkCode()) {
			return exceptionResult(result, "验证码输入有误");
		}
		String username = HttpServletRequestUtils.getString(request, "username");
		String password = HttpServletRequestUtils.getString(request, "password");
		String newPassword = HttpServletRequestUtils.getString(request, "newPassword");
		String newPassword2 = HttpServletRequestUtils.getString(request, "newPassword2");
		if(! newPassword2.equals(newPassword)) {
			return exceptionResult(result, "两次输入密码不一致");
		}
		User user = (User)request.getSession().getAttribute("user");
		if (username == null || password == null || user == null || newPassword ==null || user.getUserId() <= 0) {
			return exceptionResult(result, "传入参数有误,请检查参数");
		}
		LocalAuthExecution lae = service.changePassword(username, password, newPassword, user.getUserId());
		if(lae.getState() == LocalAuthEnum.SUCCESS.getState()) {
			result.put("success", true);
		} else {
			return exceptionResult(result, lae.getStateInfo());
		}
		return result;
	}
	
	/**
	 * 登出方法
	 */
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public void logout(HttpServletRequest request){
		request.getSession().setAttribute("user", null);
		try {
			response.sendRedirect("/o2o/index.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	
	/*
	 * private void onLogin(LocalAuth user) { Map<String, String> claims = new
	 * HashMap<String, String>(); claims.put("username", user.getUsername());
	 * claims.put("password", user.getPassword()); claims.put("name",
	 * user.getUser().getEmail()); claims.put("name", user.getUser().getName());
	 * String token = JWTUtils.getToken(claims); user.setToken(token); }
	 */
	
	/*
	 * private String renewToken(String token, String username) {
	 * redisTemplate.opsForValue().set(username, token);
	 * redisTemplate.expire(username, 15, TimeUnit.MINUTES); return token; }
	 */
}
