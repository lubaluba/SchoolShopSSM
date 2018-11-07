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
	
	//注册本地账号
	@ResponseBody
	@RequestMapping(value = "/register", method = {RequestMethod.POST})
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
			exceptionResult(result, "验证码输入有误");
		}
		String username = HttpServletRequestUtils.getString(request, "username");
		String password = HttpServletRequestUtils.getString(request, "password");
		String newPassword = HttpServletRequestUtils.getString(request, "newPassword");
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
