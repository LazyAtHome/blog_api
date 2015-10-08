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

    private static final String NAME_SPACE = Post.class.getName();

    @Override
    public List<Post> searchByPage(Post post, PageBounds pageBounds) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cond", post);
        return getSqlSession().selectList(NAME_SPACE + ".query", paramMap, pageBounds);
    }

    @Override
    public Post selectOne(Long id, Long userId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("userId", userId);
        return getSqlSession().selectOne(NAME_SPACE + ".select", paramMap);
    }

    @Override
    public void addViewCount(Long id) {
        getSqlSession().update(NAME_SPACE + ".addViewCount", id);
    }

    @Override
    public List<Post> searchByTag(String q, PageBounds pageBounds) {
        return getSqlSession().selectList(NAME_SPACE + ".queryByTag", q, pageBounds);
    }

}
