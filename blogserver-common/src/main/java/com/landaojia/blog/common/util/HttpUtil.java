package com.landaojia.blog.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * http请求工具
 * 
 * @author liuxi
 */
public class HttpUtil {
	/**
	 * 取得请求功能的路径
	 */
	public static String getPageUri(HttpServletRequest request) {
		String uri = request.getRequestURI().replaceAll("/+", "/");
		String contextPath = request.getContextPath();
		if ("/".equals(contextPath) || "".equals(contextPath)) {
			uri = uri.substring(1);
		} else {
			uri = uri.substring(contextPath.length() + 1);
		}

		int dotPos = uri.indexOf(".");
		if (dotPos != -1) {
			uri = uri.substring(0, dotPos);
		}
		return uri;
	}

	/**
	 * 取得请求公网的ip
	 */
	public static String getRequestIp(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");

		if (ip == null || "".equals(ip.trim())) {
			ip = req.getHeader("X-Real-IP");
		}
		if (ip == null || "".equals(ip.trim())) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || "".equals(ip.trim())) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || "".equals(ip.trim())) {
			ip = req.getRemoteAddr();
		}
		if (ip == null || "".equals(ip.trim())) {
			ip = "";
		}

		return ip;
	}
	
	/**
	 * 获取请求的所有参数
	 */
	public static String getAllParamsStr( HttpServletRequest request ){
		//----打印出所有请求参数---------------
		Set<String> keySet = request.getParameterMap().keySet();
		StringBuilder sb = new StringBuilder();
		if (keySet != null && keySet.size() > 0) {
			int size = keySet.size();
			int i = 0;
			for (String key : keySet) {
				i += 1;
				
				String value = request.getParameter(key);				
				sb.append(key).append("=").append(value);
				if( i != size ){
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}
}
