package com.landaojia.blogserver.common.exception;


import com.landaojia.blogserver.common.log.LogAble;
import com.landaojia.blogserver.common.result.JsonResult;
import com.landaojia.blogserver.common.util.DateUtil;
import com.landaojia.blogserver.common.util.HttpUtil;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Set;

/**
 * 异常拦截器
 * (1)当发生异常时打印出异常信息
 * (2)当发生异常时，返回统一的AjaxResult对象
 * @author liuxi
 */
public class ControllerExceptionResolver implements HandlerExceptionResolver,LogAble {


	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
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
		log().info( "ip:" + HttpUtil.getRequestIp(request) + " request:" + HttpUtil.getPageUri( request ) + " params:" + sb.toString() );
		
		//----对于异常类型处理----------------
		String idf = "【" + DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss") + "(" + (int)( Math.random() * 99999999 ) + ")】";
		String errMsg = "";
		String errCode="";
		if (ex instanceof CommonException) {
			CommonException ce = ( CommonException ) ex;
			errMsg = ce.getMessage();
			log().error( ce.getCode() + "-" + ce.getMessage() + "【" + ce.getThrowAt() + "】" );
		} else {
			errMsg = "系统异常" + idf;
			log().error( errMsg, ex );
		}
		
		JsonResult jr = JsonResult.failure( errMsg );
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().println( jr.toJson() );
		} catch (Exception e) {
			log().error( "====ERROR(ajax)====", e );
		}

		return new ModelAndView();
	}
}
