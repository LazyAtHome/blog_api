package com.landaojia.blog.user.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.landaojia.blog.common.result.JsonResult;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	
	@ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public JsonResult create(String username, String password){
        return JsonResult.success("ok");
    }

	@ResponseBody
    @RequestMapping(value ="/{id}",  method = RequestMethod.GET)
    public JsonResult get(Long id){
        return JsonResult.success("ok");
    }
	
	@ResponseBody
    @RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
    public JsonResult search(Long id){
        return JsonResult.success("ok");
    }
	
	@ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResult update(Long id){
        return JsonResult.success("ok");
    }
	
	@ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResult create(Long id){
        return JsonResult.success("ok");
    }

}
