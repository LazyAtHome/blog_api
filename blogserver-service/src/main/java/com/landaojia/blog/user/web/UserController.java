package com.landaojia.blog.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.common.validation.Validator;
import com.landaojia.blog.role.Authorization;
import com.landaojia.blog.user.entity.User;
import com.landaojia.blog.user.service.UserService;
import com.landaojia.mvc.Current;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Created by JUN on 15/9/11.
 */
@Api(value = "users", description = "User API",basePath = "/users",position = 1)
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    CommonDao commonDao;

    @Resource
    UserService userService;
    
    @Authorization(ignoreCheck = true)
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", httpMethod = "POST", notes = "用户登录", response = JsonResult.class)
    
    public JsonResult login(@ApiParam(required = true) String userName, @ApiParam(required = true) String password) {
        User user = userService.login(userName, password);
        return JsonResult.success(user);
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ApiOperation(value = "用户注销", httpMethod = "GET", notes = "用户注销", response = JsonResult.class)
    public JsonResult logout(HttpSession session) {
//        userService.logout(0L);
        return JsonResult.success("ok");
    }
    
    @Authorization(ignoreCheck = true)
    @ResponseBody
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册", httpMethod = "POST", notes = "用户注册", response = JsonResult.class)
    public JsonResult register(@ApiParam(required = true) @RequestBody User user) {
        new Validator(user)
        .forProperty("userName").notNull().notBlank().length(7, 20).hasNoChineseWord()
        .forProperty("email").notNull().maxLength(100).isEmail().hasNoChineseWord().custom(commonDao.search(new User().setEmail(user.getEmail())).size() == 0, "该邮箱地址已被注册")
        .forProperty("cryptedPassword").notNull().notBlank().length(7, 20).hasNoChineseWord().check();
        userService.registerUser(user);
        return JsonResult.success("ok");
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户信息", httpMethod = "GET", notes = "获取当前用户信息", response = JsonResult.class)
    public JsonResult current(@ApiParam(required = true) Current current) {
        User user = current.getCurrentUser();
        user.setCryptedPassword("");
        return JsonResult.success(user);
    }
}
