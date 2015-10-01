package com.landaojia.blog.post.service;

import com.landaojia.blog.common.dao.Pagination;
import com.landaojia.blog.post.entity.PostComment;

/**
 * contains all of the operations about PostComment entity;
 * 
 * @author Jason 2015年9月28日
 */
public interface PostCommentService {

    /**
     * save a post comment, contains insert and update operations distinguishing by if the value of id is null;
     * 
     * @param postComment
     * @return
     */
    PostComment save(PostComment postComment);

    void delete(Long userId, Long commentId);

    /**
     * find all of the comments from one post;
     * 
     * @param postId
     * @param page
     * @param limit
     * @return
     */
    Pagination<PostComment> findByPostIdInPage(Long postId, int page, int limit);

}
