package com.landaojia.blog.user.web;

import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.common.util.BaseController;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by JUN on 15/9/11.
 */
@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", httpMethod = "POST", response = JsonResult.class, notes = "user login")
    public JsonResult login(String uname, String password){
        return JsonResult.success(uname);
    }
    
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ApiOperation(value = "/logout", httpMethod = "GET", response = JsonResult.class, notes = "logout")
    public JsonResult logout(){
        return JsonResult.success("ok");
    }

}
