package com.landaojia.blog.post.service;

import java.util.List;

import com.landaojia.blog.post.entity.Post;

public interface PostService {

    public void create(Post post);

    public List<Post> queryAll();

    public Post queryById(Long id);

    public void update(Long id, Post post);

    public void delete(Long id);

}
