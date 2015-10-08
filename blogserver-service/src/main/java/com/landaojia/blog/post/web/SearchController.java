package com.landaojia.blog.post.web;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.post.service.PostService;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private PostService postService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public JsonResult search(@RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "content") String by, @RequestParam String q)
            throws UnsupportedEncodingException {
        return JsonResult.success(this.postService.search(page, limit, by, new String(q.getBytes("iso-8859-1"), "utf-8")));
    }
}
