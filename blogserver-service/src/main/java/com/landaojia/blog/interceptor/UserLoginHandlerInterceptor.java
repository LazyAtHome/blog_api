package com.landaojia.blog.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.common.util.EncryptUtil;
import com.landaojia.blog.role.Authorization;
import com.landaojia.blog.role.UserRole;
import com.landaojia.blog.user.entity.User;

/***
 * check login and get user id
 * 
 * @author JiangXiaoYong 2015年9月16日 下午3:41:07
 */
public class UserLoginHandlerInterceptor implements HandlerInterceptor {

    @Resource
    private CommonDao commonDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authorization authorization = ((HandlerMethod) handler).getMethodAnnotation(Authorization.class);
        // if the annotation not exist or the value of property named ignoreCheck of this annotation is true, we should
        // pass the request;
        if (authorization == null || authorization.ignoreCheck()) return true;
        String accessToken = request.getHeader("accessToken");
        if (Strings.isNullOrEmpty(accessToken)) throw new CommonException(CommonExceptionCode.USER_NOT_LOGIN);
        // obtain the userId from user's access token
        Long userId = null;
        try {
            String[] userInfo = EncryptUtil.decrypt(accessToken).split(" ");
            userId = Long.valueOf(userInfo[userInfo.length - 1]);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(CommonExceptionCode.USER_NOT_EXISTS);
        }
        User user = commonDao.findById(User.class, userId);
        if (user == null) throw new CommonException(CommonExceptionCode.USER_NOT_EXISTS);
        UserRole[] roles = authorization.role();
        // if the size of role equals 0 ,pass the request
        if (roles.length == 0) return true;
        // check the role of user ,whether which is in role list of this method
        UserRole userRole = UserRole.getRole(user.getRole());
        for (UserRole role : roles) {
            if (role.equals(userRole)) {
                request.setAttribute("userId", userId);
                return true;
            }
        }
        throw new CommonException(CommonExceptionCode.POST_NO_AUTH);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
