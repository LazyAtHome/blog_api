package com.landaojia.blog.post.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.landaojia.blog.common.dao.BaseEntity;
import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.dao.Pagination;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.common.util.DateUtil;
import com.landaojia.blog.post.entity.PostComment;
import com.landaojia.blog.post.service.PostCommentService;
import com.landaojia.blog.role.UserRole;
import com.landaojia.blog.user.entity.User;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    @Resource
    CommonDao commonDao;

    @Override
    @Transactional
    public PostComment save(PostComment postComment, User currentUser) {
        if(postComment.getId() == null){
            postComment.setUserId(currentUser.getId());
            postComment.setCreatedBy(currentUser.getUserName());
            postComment.setCreatedDate(DateUtil.getCurrentDate());
            postComment.setUpdatedBy(currentUser.getUserName());
            postComment.setUpdatedDate(DateUtil.getCurrentDate());
            postComment = commonDao.insert(postComment);
        } else {
            PostComment pc = commonDao.findById(PostComment.class, postComment.getId());
            if(pc == null) throw new CommonException(CommonExceptionCode.POST_COMMENT_NOT_EXISTS);
            if(pc.getUserId().equals(currentUser.getId()) || currentUser.getRole().equals(UserRole.ADMIN.getValue())){
                pc.setContent(postComment.getContent());
                pc.setUpdatedBy(currentUser.getUserName());
                pc.setUpdatedDate(DateUtil.getCurrentDate());
                postComment = commonDao.update(pc);
            } else throw new CommonException(CommonExceptionCode.POST_COMMENT_NO_RIGHT_UPDATE);
        }
        return postComment;
    }

    @Override
    @Transactional
    public void delete(Long userId, Long commentId) {
        if (userId == null || commentId == null) throw new CommonException(CommonExceptionCode.E999999);
        PostComment comment = commonDao.findById(PostComment.class, commentId);
        if (comment == null) throw new CommonException(CommonExceptionCode.POST_COMMENT_NOT_EXISTS);
        //the user as the creator of PostComment who can freely delete it.
        if (comment.getUserId().compareTo(userId) == 0) {
            commonDao.removeById(PostComment.class, commentId);
            return;
        }
        //if it's not creator, we rule that only the user having ADMIN role can delete it.
        User user = commonDao.findById(User.class, userId);
        if (user == null) throw new CommonException(CommonExceptionCode.USER_NOT_EXISTS);
        if (UserRole.ADMIN.equals(UserRole.getRole(user.getRole()))) {
            commonDao.removeById(PostComment.class, commentId);
            return;
        }
        throw new CommonException(CommonExceptionCode.POST_COMMENT_NO_RIGHT_DELETE);
    }

    @Override
    public Pagination<PostComment> findByPostIdInPage(Long postId, int page, int limit){
        if(postId == null) return new Pagination<PostComment>(page, limit);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cond.postId", postId);
        paramMap.put("cond.deletedFlag", BaseEntity.DELETED_FLAG_NO);
        return commonDao.searchByPage(new Pagination<PostComment>(PostComment.class, paramMap, page, limit, Order.formString("createdDate.desc").toArray(new Order[0])));
    }
    
}
