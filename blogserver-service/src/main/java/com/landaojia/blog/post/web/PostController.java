package com.landaojia.blog.post.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.common.util.HttpUtil;
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

    @Authorization(role = { UserRole.EDITOR, UserRole.ADMIN })
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public JsonResult create(@RequestBody Post post, Current current) {
        Validator v = new Validator(post);
        v.forProperty("title").notNull().notBlank().maxLength(50);
        v.forProperty("content").notNull().notBlank().maxLength(4000);
        v.check();
        return JsonResult.success(postService.create(post, current.getCurrentUser()));
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResult queryById(@PathVariable("id") Long id, HttpServletRequest request, Current current) {
        Post post = this.postService.queryById(id, current.getCurrentUser());
        this.postService.addViewCount(HttpUtil.getRequestIp(request), id);
        return JsonResult.success(post);
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public JsonResult queryAll(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit) {
        return JsonResult.success(this.postService.queryAll(page, limit));
    }

    @Authorization
    @ResponseBody
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public JsonResult queryByUserId(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit, Current current) {
        return JsonResult.success(this.postService.queryByUserId(page, limit, current.getCurrentUser()));
    }

    @Authorization(role = { UserRole.EDITOR, UserRole.ADMIN })
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResult update(@PathVariable("id") Long id, @RequestBody Post post, Current current) {
        Validator v = new Validator(post);
        v.forProperty("title").maxLength(50);
        v.forProperty("content").maxLength(4000);
        v.check();
        this.postService.update(id, post, current.getCurrentUser());
        return JsonResult.success("ok");
    }

    @Authorization(role = { UserRole.EDITOR, UserRole.ADMIN })
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResult delete(@PathVariable("id") Long id) {
        this.postService.delete(id);
        return JsonResult.success("ok");
    }
    
    @Authorization(role = {UserRole.EDITOR, UserRole.ADMIN})
    @ResponseBody
    @RequestMapping(value="uploadFile/{postId}", method= RequestMethod.POST)
    public JsonResult addAttachment(MultipartFile file,@PathVariable Long postId, Current current){
    	return JsonResult.success(postService.addAttachment(file, postId, current.getCurrentUser(), current.getWebRootPath()));
    }

}
