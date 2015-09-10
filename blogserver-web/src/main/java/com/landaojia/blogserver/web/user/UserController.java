package com.landaojia.blogserver.web.user;

import com.landaojia.blogserver.common.result.JsonResult;
import com.landaojia.blogserver.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by JUN on 15/9/8.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping("/register")
    public JsonResult register(String uname,String pwd){

        userService.registerUser(uname,pwd);
        return JsonResult.success("注册成功！");
    }

    @ResponseBody
    @RequestMapping("/test")
    public JsonResult test(String uname,String pwd){
        return JsonResult.success("成功！");
    }


}