package com.landaojia.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.landaojia.blog.blog.common.dao.CommonDao;
import com.landaojia.blog.user.dao.UserDao;
import com.landaojia.blog.user.entity.User;

/**
 * Register the current Object into the method in controller
 * @author Jason
 *
 * 2015年9月9日
 */
public class MethodArgumentResolver implements HandlerMethodArgumentResolver {
    
    public static final String SESSION_LOGIN = "session-login";

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
	      HttpSession session = request.getSession();
	     User user = (User) session.getAttribute(SESSION_LOGIN);
		return new Current(context.getBean(CommonDao.class), context.getBean(UserDao.class), user);
	}

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().isAssignableFrom(Current.class);
	}

}