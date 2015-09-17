package com.landaojia.blog.user.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.user.entity.User;
import com.landaojia.blog.user.service.UserService;
import com.landaojia.mvc.Current;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

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
    @ApiOperation(value = "用户登录", httpMethod = "POST", notes = "用户登录", response = JsonResult.class)
    public JsonResult login(@ApiParam(required = true) String userName, @ApiParam(required = true) String password, HttpSession session) {
        if (session.getAttribute(Current.SESSION_LOGIN) == null) {
            User user = userService.login(userName, password);
            session.setAttribute(Current.SESSION_LOGIN, user.getId());
            return JsonResult.success(user);
        }
        throw new CommonException(CommonExceptionCode.USER_IS_LOGINED);
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ApiOperation(value = "用户注销", httpMethod = "GET", notes = "用户注销", response = JsonResult.class)
    public JsonResult logout(HttpSession session) {
        Long userId = (Long) session.getAttribute(Current.SESSION_LOGIN);
        if (userId != null) {
            // other logics executed in service layer;
            session.removeAttribute(Current.SESSION_LOGIN);
            userService.logout(userId);
        }
        return JsonResult.success("ok");
    }

    @ResponseBody
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册", httpMethod = "POST", notes = "用户注册", response = JsonResult.class)
    public JsonResult register(@ApiParam(required = true) User user) {
        userService.registerUser(user);
        return JsonResult.success("ok");
    }

    @ResponseBody
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户信息", httpMethod = "GET", notes = "获取当前用户信息", response = JsonResult.class)
    public JsonResult current(@ApiParam(required = true) Current current) {
        return JsonResult.success(current.getCurrentUser());
    }
}
