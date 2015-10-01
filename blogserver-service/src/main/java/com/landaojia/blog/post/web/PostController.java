package com.landaojia.blog.post.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.common.validation.Validator;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.post.service.PostService;
import com.landaojia.blog.role.Authorization;
import com.landaojia.blog.role.UserRole;
import com.landaojia.mvc.Current;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Resource
    private PostService postService;

    @Authorization(role = {UserRole.EDITOR})
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public JsonResult create(@RequestBody Post post, Current current) {
        validate(post);
        this.postService.create(post, current.getCurrentUser());
        return JsonResult.success("ok");
    }
    
    @Authorization(ignoreCheck = true)
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public JsonResult queryAll(@RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        return JsonResult.success(this.postService.queryAll(page, limit));
    }

    @Authorization(ignoreCheck = true)
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResult queryById(@PathVariable("id") Long id) {
        return JsonResult.success(this.postService.queryById(id));
    }

    @Authorization(role = {UserRole.EDITOR, UserRole.ADMIN})
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResult update(@PathVariable("id") Long id, @RequestBody Post post, Current current) {
        validate(post);
        this.postService.update(id, post, current.getCurrentUser());
        return JsonResult.success("ok");
    }

    @Authorization(role = {UserRole.EDITOR, UserRole.ADMIN})
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResult delete(@PathVariable("id") Long id) {
        this.postService.delete(id);
        return JsonResult.success("ok");
    }
    
    private void validate(Post post) {
        Validator v = new Validator(post);
        v.forProperty("title").notNull().notBlank().maxLength(50);
        v.forProperty("content").notNull().notBlank().maxLength(500);
        v.check();
    }
    
}
