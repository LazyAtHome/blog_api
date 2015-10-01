package com.landaojia.blog.post.entity;

import com.landaojia.blog.common.dao.BaseEntity;

public class PostComment extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long userId;
    
    private Long postId;
    
    private String content;
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getPostId() {
        return postId;
    }
    
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    
    
}
