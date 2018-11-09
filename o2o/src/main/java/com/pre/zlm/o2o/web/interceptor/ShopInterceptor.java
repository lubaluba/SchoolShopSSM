package com.pre.zlm.o2o.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pre.zlm.o2o.entity.User;

/**
 * 登录校验拦截器
 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在多个Interceptor，
 * 然后SpringMVC会根据声明的前后顺序一个接一个的执行，
 * 而且所有的Interceptor中的preHandle方法都会在Controller方法调用之前调用。
 * SpringMVC的这种Interceptor链式结构也是可以进行中断的，
 * 这种中断方式是令preHandle的返回值为false，当preHandle的返回值为false的时候整个请求就结束了。
 */
public class ShopInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//从session取出用户信息
		Object userObj = request.getSession().getAttribute("user");
		if (userObj != null) {
			//检查用户信息的userId是否不为空并且是否是店家用户
			User user = (User) userObj;
			if (user != null && user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1 && user.getUserType() == 2)
				//通过验证则返回true,拦截器返回true后,用户接下来的操作才得以正常执行
				return true;
		}
		response.getWriter().println("needlogin");
		return false;
	}
	

}
