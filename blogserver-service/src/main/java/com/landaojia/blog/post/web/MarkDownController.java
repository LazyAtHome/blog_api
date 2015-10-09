package com.landaojia.blog.post.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.rjeschke.txtmark.Processor;
import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.common.util.HttpUtil;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.post.service.PostService;
import com.landaojia.mvc.Current;

@RestController
@RequestMapping("/md")
public class MarkDownController {
    
    @Resource
    private PostService postService;
    
    @ResponseBody
    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public JsonResult preview(@RequestBody Map<String, String> map) {
        return JsonResult.success(Processor.process(map.get("txt")));
    }
    
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResult queryById(@PathVariable("id") Long id, HttpServletRequest request, Current current) {
        Post post = this.postService.queryById(id, current.getCurrentUser());
        post.setContent(Processor.process(post.getContent()));
        this.postService.addViewCount(HttpUtil.getRequestIp(request), id);
        return JsonResult.success(post);
    }
    
}
