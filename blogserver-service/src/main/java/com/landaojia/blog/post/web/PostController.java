package com.landaojia.blog.post.web;

import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.post.service.PostService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
@Api(basePath = "/posts",value = "posts",description = "Post API",position = 2)
@RestController
@RequestMapping("/posts")
public class PostController {

    @Resource
    private PostService postService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public JsonResult create(Post post, HttpSession session) {
        postService.create(post, session);
        return JsonResult.success("ok");
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public JsonResult queryAll() {
        // TODO pagination
        return JsonResult.success(postService.queryAll());
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResult queryById(@PathVariable("id") Long id) {
        return JsonResult.success(postService.queryById(id));
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResult update(@PathVariable("id") Long id, Post post, HttpSession session) {
        postService.update(id, post, session);
        return JsonResult.success("ok");
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResult delete(@PathVariable("id") Long id, HttpSession session) {
        postService.delete(id, session);
        return JsonResult.success("ok");
    }

}
