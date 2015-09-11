package com.landaojia.blog.user.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.landaojia.blog.blog.common.result.JsonResult;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public JsonResult create(@PathVariable("username") String username, @PathVariable("password") String password){
        return JsonResult.success("ok");
    }

	@ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public JsonResult get(@PathVariable("id") Long id){
        return JsonResult.success("ok");
    }
	
	@ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public JsonResult search(@PathVariable("id") Long id){
        return JsonResult.success("ok");
    }
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public JsonResult update(@PathVariable("id") Long id){
        return JsonResult.success("ok");
    }
	
	@ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public JsonResult create(@PathVariable("id") Long id){
        return JsonResult.success("ok");
    }

}
