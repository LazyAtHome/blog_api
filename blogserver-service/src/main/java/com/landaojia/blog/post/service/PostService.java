package com.landaojia.blog.post.service;

import java.util.Map;

import com.landaojia.blog.post.entity.Post;

public interface PostService {

    public void create(Post post);

    public Map<String, Object> queryAll(Integer page, Integer limit);

    public Post queryById(Long id);

    public void update(Long id, Post post);

    public void delete(Long id);

}
