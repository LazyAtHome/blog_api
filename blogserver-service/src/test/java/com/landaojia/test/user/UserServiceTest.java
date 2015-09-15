package com.landaojia.test.user;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.landaojia.blog.user.entity.User;
import com.landaojia.blog.user.service.UserService;
import com.landaojia.mvc.Current;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/blogserver-dao.xml","classpath:META-INF/spring/blogserver-web-config.xml", "classpath:META-INF/spring/blogserver-context.xml"})
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    
    // about sesion -----------------start----------
    protected MockHttpSession session;
    protected MockHttpServletRequest request;

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
        user.setCryptedPasswordConfirm("123456dasda");
        user.setEmail("tom@gmail.com");
        userService.registerUser(user);
    }
    
    @Test
    public void loginTest(){
        startSession();
        userService.login("jason_lee", "landaojiaTEST", session);
        System.out.println(session.getAttribute(Current.SESSION_LOGIN).toString());
    }
    
    @Test
    public void logoutTest(){
        loginTest();
        userService.logout(session);
        endSession();
    }
    
}
