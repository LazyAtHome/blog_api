package com.landaojia.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.user.dao.UserDao;

/**
 * Register the current Object into the method in controller
 * @author Jason
 *
 * 2015年9月9日
 */
public class MethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return new Current(context.getBean(CommonDao.class), context.getBean(UserDao.class), (Long) request.getSession().getAttribute(Current.SESSION_LOGIN));
	}

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().isAssignableFrom(Current.class);
	}

}
