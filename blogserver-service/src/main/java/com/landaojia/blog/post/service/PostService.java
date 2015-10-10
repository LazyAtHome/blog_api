package com.landaojia.blog.post.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.user.entity.User;

public interface PostService {

    public void create(Post post, User user);

    public Post queryById(Long id, User user);

    public Map<String, Object> queryAll(Integer page, Integer limit);

    public Map<String, Object> queryByUserId(Integer page, Integer limit, User user);

    public void update(Long id, Post post, User user);

    public void delete(Long id);

    public void addViewCount(String ip, Long id);

    public Map<String, Object> search(Integer page, Integer limit, String by, String q);
    
    String addAttachment(MultipartFile file, Long postId, User currentUser, String webRootPath);

}
