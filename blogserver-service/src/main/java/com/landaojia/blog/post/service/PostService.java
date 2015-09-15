package com.landaojia.blog.post.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.landaojia.blog.post.entity.Post;

public interface PostService {

    public void create(Post post, HttpSession session);

    public List<Post> queryAll();

    public Post queryById(Long id);

    public void update(Long id, Post post, HttpSession session);

    public void delete(Long id, HttpSession session);

}
