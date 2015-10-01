package com.landaojia.blog.post.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.landaojia.blog.common.dao.BaseDao;
import com.landaojia.blog.post.dao.PostDao;
import com.landaojia.blog.post.entity.Post;

@Repository
public class PostDaoImpl extends BaseDao implements PostDao {
    
    @Override
    public List<Post> searchByPage(Post post, PageBounds pageBounds) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cond", post);
        return getSqlSession().selectList("com.landaojia.blog.post.entity.Post.query", paramMap, pageBounds);
    }

    @Override
    public Post selectOne(Long id, Long userId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("userId", userId);
        return getSqlSession().selectOne("com.landaojia.blog.post.entity.Post.select", paramMap);
    }

    @Override
    public void addViewCount(Long id) {
        getSqlSession().update("com.landaojia.blog.post.entity.Post.addViewCount", id);
    }

}
