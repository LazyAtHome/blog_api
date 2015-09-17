package com.landaojia.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.common.validation.ValidateException;

public class HttpHandlerExceptionResolver implements HandlerExceptionResolver {

	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> badRequestReason = new HashMap<String, Object>();
		try {
			if (ex instanceof HttpRequestMethodNotSupportedException) {
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			} else if (ex instanceof BindException) {
				response.setStatus(HttpServletResponse.SC_OK);
				badRequestReason.put("validateError", convertToValidateException((BindException) ex).getErrors());
				badRequestReason.put("error", "数据绑定失败");
			} else if (ex instanceof ValidateException) {
				response.setStatus(HttpServletResponse.SC_OK);
				badRequestReason.put("validateError", ((ValidateException) ex).getErrors());
				badRequestReason.put("error", ((CommonException) ex).getMessage());
			} else if (ex instanceof CommonException) {
				response.setStatus(HttpServletResponse.SC_OK);
				badRequestReason.put("error", ((CommonException) ex).getMessage());
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				ex.printStackTrace();
			}
			//bad request
			if (!response.isCommitted() && !badRequestReason.isEmpty()) {
			    response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.addHeader("Pragma", "no-cache");
			    response.addHeader("Cache-Control", "no-cache");
			    response.addHeader("Expires", "0");
			    response.addHeader("Access-Control-Allow-Origin", "*");
			    JSON.writeJSONStringTo(JsonResult.failure(badRequestReason).setResponseMsg(badRequestReason.get("error").toString()), response.getWriter());
		        response.getWriter().flush();
			}
		} catch (Throwable throwable){
		}
		return new ModelAndView();
	}
	
	private ValidateException convertToValidateException(BindException bindException) {
		BindingResult bindingResult = bindException.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		Map<String, String> errors = new HashMap<String, String>();
		String fieldTypeName;
		String fieldName;
		for (FieldError fe : fieldErrors) {
			fieldName = fe.getField();
			fieldTypeName = convertClassName(bindingResult.getFieldType(fieldName));
			if (!errors.containsKey(fieldName)) {
				errors.put(fieldName, "必须为" + fieldTypeName);
			}
		}
		return new ValidateException(errors);
	}

	private String convertClassName(Class<?> clazz) {
		if (clazz.equals(Integer.class)) {
			return "整数";
		} else if (clazz.equals(Float.class)) {
			return "浮点数";
		} else if (clazz.equals(Double.class)) {
			return "双精度浮点数";
		} else if (clazz.equals(Boolean.class)) {
			return "布尔型";
		} else {
			return clazz.getSimpleName();
		}
	}

}