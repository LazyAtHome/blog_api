package com.landaojia.blog.post.service;

import java.util.Map;

import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.user.entity.User;

public interface PostService {

    public void create(Post post, User user);

    public Map<String, Object> queryAll(Integer page, Integer limit);

    public Post queryById(Long id);

    public void update(Long id, Post post, User user);

    public void delete(Long id);

}
