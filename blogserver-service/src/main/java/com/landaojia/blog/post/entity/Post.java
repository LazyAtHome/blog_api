package com.landaojia.blog.post.entity;


import com.landaojia.blog.common.dao.BaseEntity;

/**
 * 
 *
 * Auto Generated by <code>CodeGenerator</code>
 */
public class Post extends BaseEntity {
private static final long serialVersionUID = 1L;
        
private String title;//
                
private String content;//
                
private Long userId;//references to id of b_user
                
private Long viewCount;//
                        
/**
 * return 
 */
public String getTitle() {
        return title;
        }

/**
 * @param title
 *            
 */
public void setTitle(String title) {
        this.title = title;
        }
                
/**
 * return 
 */
public String getContent() {
        return content;
        }

/**
 * @param content
 *            
 */
public void setContent(String content) {
        this.content = content;
        }
                
/**
 * return references to id of b_user
 */
public Long getUserId() {
        return userId;
        }

/**
 * @param userId
 *            references to id of b_user
 */
public void setUserId(Long userId) {
        this.userId = userId;
        }
                
/**
 * return 
 */
public Long getViewCount() {
        return viewCount;
        }

/**
 * @param viewCount
 *            
 */
public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
        }
                        }