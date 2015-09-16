package com.landaojia.blog.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.landaojia.blog.annotation.LoginRequired;
import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.threadlocal.UserThreadLocal;
import com.landaojia.blog.user.entity.User;
import com.landaojia.mvc.Current;

/***
 * check login and put user information into threadlocal
 * @author JiangXiaoYong
 *
 * 2015年9月16日 下午3:41:07
 */
public class UserLoginHandlerInterceptor implements HandlerInterceptor {
    
    @Resource
    private CommonDao commonDao;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        LoginRequired annotation = method.getAnnotation(LoginRequired.class);
        if (annotation != null) {
            Long userId = (Long) request.getSession().getAttribute(Current.SESSION_LOGIN);
            if (null == userId) {
                throw new CommonException(CommonExceptionCode.USER_NOT_LOGIN);
            }
            User user = this.commonDao.findById(User.class, userId);
            UserThreadLocal.set(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
    }

}
