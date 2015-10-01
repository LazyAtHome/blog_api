package com.landaojia.blog.post.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.common.result.JsonResult;
import com.landaojia.blog.common.util.DateUtil;
import com.landaojia.blog.common.validation.Validator;
import com.landaojia.blog.post.entity.PostComment;
import com.landaojia.blog.post.service.PostCommentService;
import com.landaojia.blog.role.Authorization;
import com.landaojia.blog.role.UserRole;
import com.landaojia.blog.user.entity.User;
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
    
    @Authorization
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public JsonResult save(@RequestBody PostComment comment, Current current){
        new Validator(comment)
        .forProperty("content").notBlank().length(5, 255)
        .forProperty("postId").notNull().check();
        User currentUser = current.getCurrentUser();
        if(comment.getId() == null){
            comment.setUserId(currentUser.getId());
            comment.setCreatedBy(currentUser.getUserName());
            comment.setCreatedDate(DateUtil.getCurrentDate());
            comment.setUpdatedBy(currentUser.getUserName());
            comment.setUpdatedDate(DateUtil.getCurrentDate());
        } else {
            PostComment pc = commonDao.findById(PostComment.class, comment.getId());
            if(pc == null) throw new CommonException(CommonExceptionCode.POST_COMMENT_NOT_EXISTS);
            if(pc.getUserId().equals(currentUser.getId()) || current.getCurrentUserRole().equals(UserRole.ADMIN)){
                pc.setContent(comment.getContent());
                pc.setUpdatedBy(currentUser.getUserName());
                pc.setUpdatedDate(DateUtil.getCurrentDate());
                comment = pc;
            } else throw new CommonException(CommonExceptionCode.POST_COMMENT_NO_RIGHT_UPDATE);
        }
        return JsonResult.success(postCommentService.save(comment));
    }
    
    @Authorization
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public JsonResult delete(Long commentId, Current current){
        postCommentService.delete(current.getCurrentUser().getId(), commentId);
        return JsonResult.success("ok");
    }
}
