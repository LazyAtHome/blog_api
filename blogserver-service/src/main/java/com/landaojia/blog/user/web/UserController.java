package com.landaojia.blog.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.user.entity.User;
import com.landaojia.blog.user.service.UserService;
import com.landaojia.mvc.Current;

/**
 * Created by JUN on 15/9/11.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    
    @Resource
    CommonDao commonDao;
    
    @Resource
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult login(String userName, String password, HttpServletRequest req){
        userService.login(userName, password, req.getSession());
        return JsonResult.success("ok");
    }
    
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JsonResult logout(HttpServletRequest req){
        req.getSession().removeAttribute(Current.SESSION_LOGIN);
        return JsonResult.success("ok");
    }
    
    @ResponseBody
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public JsonResult register(User user){
        userService.registerUser(user);
        return JsonResult.success("ok");
    }
}
