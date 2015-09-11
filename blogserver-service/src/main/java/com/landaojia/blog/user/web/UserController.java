package com.landaojia.blog.user.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.landaojia.blog.blog.common.result.JsonResult;

/**
 * Created by JUN on 15/9/11.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult login(@PathVariable("username") String username, @PathVariable("password") String password){
        return JsonResult.success("ok");
    }
    
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JsonResult logout(){
        return JsonResult.success("ok");
    }

}
