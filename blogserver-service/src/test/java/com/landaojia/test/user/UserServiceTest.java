package com.landaojia.test.user;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.landaojia.blog.user.entity.User;
import com.landaojia.blog.user.service.UserService;
import com.landaojia.test.AbstractJunitContextTests;


public class UserServiceTest extends AbstractJunitContextTests {
    
    // about sesion -----------------start----------
    protected MockHttpSession session;
    protected MockHttpServletRequest request;
    
    private String accessToken;

    protected void startSession() {
        session = new MockHttpSession();
    }

    protected void endSession() {
        session.clearAttributes();
        session = null;
    }

    protected void startRequest() {
        request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    protected void endRequest() {
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).requestCompleted();
        RequestContextHolder.resetRequestAttributes();
        request = null;
    }
    // about sesion -----------------end----------
    
    @Resource
    UserService userService;
    
    @Test
    public void registerTest() {
        User user = new User();
        user.setUserName("tommyhanks");
        user.setCryptedPassword("123456dasda");
        user.setEmail("tom@gmail.com");
        userService.register(user);
    }
    
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void loginTest(){
        startSession();
        User user = userService.login("yanpengtest", "testtestyanpeng");
        accessToken = user.getAccessToken();
        System.out.println("accessToken:::::::::::::"+user.getAccessToken());
    }
    
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void logoutTest(){
        loginTest();
        userService.logout(0L);
        endSession();
    }
    
}
