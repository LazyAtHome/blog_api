package com.landaojia.blog.post.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.post.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Resource
    private PostService postService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public JsonResult create(Post post, HttpSession session) {
        this.postService.create(post, session);
        return JsonResult.success("ok");
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public JsonResult queryAll() {
        // TODO pagination
        return JsonResult.success(this.postService.queryAll());
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResult queryById(@PathVariable("id") Long id) {
        return JsonResult.success(this.postService.queryById(id));
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResult update(@PathVariable("id") Long id, Post post, HttpSession session) {
        this.postService.update(id, post, session);
        return JsonResult.success("ok");
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResult delete(@PathVariable("id") Long id, HttpSession session) {
        this.postService.delete(id, session);
        return JsonResult.success("ok");
    }

}
