package com.pre.zlm.o2o.utils;
import javax.servlet.http.HttpServletRequest;
public class HttpServletRequestUtils {
	
	public static Integer getInt(HttpServletRequest request, String key) {
		try {
			return Integer.decode(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static Long getLong(HttpServletRequest request, String key) {
		try {
			return Long.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1L;
		}
	}
	
	public static Double getDouble(HttpServletRequest request, String key) {
		try {
			return Double.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1d;
		}
	}
	
	public static Float getFloat(HttpServletRequest request, String key) {
		try {
			return Float.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1f;
		}
	}
	
	public static Boolean getBoolean(HttpServletRequest request, String key) {
		try {
			return Boolean.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String getString(HttpServletRequest request,String key) {
		try {
			String result = request.getParameter(key);
			if (result != null)
				result = result.trim();
			if ("".equals(result)) {
				return null;
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
		
}
