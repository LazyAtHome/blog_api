package com.landaojia.blog.user.web;

import com.landaojia.blog.blog.common.result.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by JUN on 15/9/11.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @ResponseBody
    @RequestMapping("/login")
    public JsonResult login(){
        return JsonResult.success("登陆成功");
    }

}
