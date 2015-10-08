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
        String accessToken = request.getHeader("accessToken");
        Authorization authorization = ((HandlerMethod) handler).getMethodAnnotation(Authorization.class);
        if (Strings.isNullOrEmpty(accessToken)) {
            if (null != authorization) {
                throw new CommonException(CommonExceptionCode.USER_NOT_LOGIN);
            }
        } else {
            // obtain the userId from user's access token
            Long userId = null;
            User user = null;
            try {
                String[] userInfo = EncryptUtil.decrypt(accessToken).split(" ");
                user = this.commonDao.findById(User.class, Long.valueOf(userInfo[userInfo.length - 1]));
                userId = user.getId();
            } catch (Exception e) {
                e.printStackTrace();
                throw new CommonException(CommonExceptionCode.USER_NOT_EXISTS);
            }
            request.setAttribute("userId", userId);
            // check the role of user ,whether which is in role list of this method
            if (null != authorization) {
                UserRole[] roles = authorization.role();
                if (roles.length != 0) {
                    UserRole userRole = UserRole.getRole(user.getRole());
                    for (UserRole role : roles) {
                        if (role.equals(userRole)) {
                            return true;
                        }
                    }
                    throw new CommonException(CommonExceptionCode.POST_NO_AUTH);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
