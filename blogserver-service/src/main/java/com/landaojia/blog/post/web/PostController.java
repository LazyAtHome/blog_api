package com.landaojia.blog.post.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.landaojia.blog.annotation.LoginRequired;
import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.post.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Resource
    private PostService postService;

    @LoginRequired
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public JsonResult create(Post post) {
        this.postService.create(post);
        return JsonResult.success("ok");
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public JsonResult queryAll(@RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        return JsonResult.success(this.postService.queryAll(page, limit));
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResult queryById(@PathVariable("id") Long id) {
        return JsonResult.success(this.postService.queryById(id));
    }

    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResult update(@PathVariable("id") Long id, Post post) {
        this.postService.update(id, post);
        return JsonResult.success("ok");
    }

    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResult delete(@PathVariable("id") Long id) {
        this.postService.delete(id);
        return JsonResult.success("ok");
    }

}
