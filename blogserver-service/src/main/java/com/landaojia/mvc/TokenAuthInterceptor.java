package com.landaojia.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.landaojia.blog.annotation.LoginIgnored;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.common.util.EncryptUtil;

/**
 * validation identity of user on client;
 * @author Jason
 *
 * 2015年9月21日
 */
public class TokenAuthInterceptor implements HandlerInterceptor {

    private String[] ignorePathes = new String[0];

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        System.out.println("request path :::::::::::::::::::"+uri);
        // step1:check uri if which is in ignorePathes;
        boolean inIgnore = false;
        for (String path : ignorePathes) {
            if (uri.endsWith(path)) {
                inIgnore = true;
                break;
            }
        }
        if (null != ((HandlerMethod) handler).getMethod().getAnnotation(LoginIgnored.class)) {
            inIgnore = true;
        }
        // step2:if it's not in ignorePathes,we will check the accessToken in header
        if (!inIgnore) {
            String accessToken = request.getHeader("accessToken");
            if (Strings.isNullOrEmpty(accessToken)) throw new CommonException(CommonExceptionCode.USER_NOT_LOGIN);
            try {
                String userName_id = EncryptUtil.decrypt(accessToken);
                String[] params = userName_id.split(" ");
                request.setAttribute("user_name", params[0]);
                request.setAttribute("userId", Long.valueOf(params[1]));
            } catch (Exception e) {
                e.printStackTrace();
                throw new CommonException(CommonExceptionCode.USER_NOT_EXISTS);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    
    public String[] getIgnorePathes() {
        return ignorePathes;
    }

    
    public void setIgnorePathes(String[] ignorePathes) {
        this.ignorePathes = ignorePathes;
    }

}
