package com.landaojia.blog.post.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.landaojia.blog.post.entity.Post;

public interface PostDao {

    public List<Post> searchByPage(Post post, PageBounds pageBounds);

    public Post selectOne(Long id, Long userId);

    public void addViewCount(Long id);

}
