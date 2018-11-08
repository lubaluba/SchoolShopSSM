package com.pre.zlm.o2o.web.local;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public Map<String, Object> logout(HttpServletRequest request){
		Map<String, Object> result = new HashMap<>();
		request.getSession().setAttribute("user", null);
		result.put("success", true);
		return result;
	}
}
