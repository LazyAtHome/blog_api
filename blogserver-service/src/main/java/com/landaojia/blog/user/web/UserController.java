package com.landaojia.blog.user.web;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.user.entity.User;
import com.landaojia.blog.user.service.UserService;
import com.landaojia.mvc.Current;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    @ApiOperation(value = "用户登录",httpMethod = "POST",notes = "用户登录",response = JsonResult.class)
    public JsonResult login(@ApiParam(required = true) @RequestParam String  userName,
                            @ApiParam(required = true) @RequestParam String password, HttpServletRequest req){
        userService.login(userName, password, req.getSession());
        return JsonResult.success("ok");
    }
    
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ApiOperation(value = "用户注销",httpMethod = "GET",notes = "用户注销",response = JsonResult.class)
    public JsonResult logout(HttpServletRequest req){
        userService.logout(req.getSession());
        return JsonResult.success("ok");
    }
    
    @ResponseBody
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册",httpMethod = "POST",notes = "用户注册",response = JsonResult.class)
    public JsonResult register(@ApiParam(required = true) @RequestParam User user){
        userService.registerUser(user);
        return JsonResult.success("ok");
    }
    
    @ResponseBody
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户信息",httpMethod = "GET",notes = "获取当前用户信息",response = JsonResult.class)
    public JsonResult current(@ApiParam(required = true) @RequestParam Current current){
        return JsonResult.success(current.getCurrentUser());
    }
}
