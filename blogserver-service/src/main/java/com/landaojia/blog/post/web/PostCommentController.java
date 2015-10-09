package com.landaojia.blog.post.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.common.validation.Validator;
import com.landaojia.blog.post.entity.PostComment;
import com.landaojia.blog.post.service.PostCommentService;
import com.landaojia.blog.role.Authorization;
import com.landaojia.mvc.Current;

/**
 * 
 * @author Jason
 *
 * 2015年9月28日
 */
@Controller
@RequestMapping("/postComments")
public class PostCommentController {
    
    @Resource
    PostCommentService postCommentService;
    
    @Resource
    CommonDao commonDao;
    
    /**
     * This method integrated two kinds of operations which is adding and updating, and we can distinguish these operations by whether they have id.
     * @param comment
     * @param current
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public JsonResult save(@RequestBody PostComment comment, Current current){
        new Validator(comment)
        .forProperty("content").notBlank().length(5, 255)
        .forProperty("postId").notNull().check();
        return JsonResult.success(postCommentService.save(comment, current.getCurrentUser()));
    }
    
    @Authorization
    @ResponseBody
    @RequestMapping(value="/{commentId}", method = RequestMethod.DELETE)
    public JsonResult delete(@PathVariable Long commentId, Current current){
        postCommentService.delete(current.getCurrentUser().getId(), commentId);
        return JsonResult.success("ok");
    }
}
