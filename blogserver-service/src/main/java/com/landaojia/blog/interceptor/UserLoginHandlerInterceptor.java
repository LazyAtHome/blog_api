package com.landaojia.blog.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.landaojia.blog.annotation.LoginIgnored;
import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.common.util.EncryptUtil;

/***
 * check login and get user id
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
        if (null == ((HandlerMethod) handler).getMethod().getAnnotation(LoginIgnored.class)) {
            String accessToken = request.getHeader("accessToken");
            if (Strings.isNullOrEmpty(accessToken)) throw new CommonException(CommonExceptionCode.USER_NOT_LOGIN);
            Long userId = null;
            try {
                String[] userInfo = EncryptUtil.decrypt(accessToken).split(" ");
                userId = Long.valueOf(userInfo[userInfo.length - 1]);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CommonException(CommonExceptionCode.USER_NOT_EXISTS);
            }
            request.setAttribute("userId", userId);
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
