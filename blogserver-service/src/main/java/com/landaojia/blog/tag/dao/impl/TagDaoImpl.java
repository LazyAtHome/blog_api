package com.landaojia.blog.tag.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.landaojia.blog.common.dao.BaseDao;
import com.landaojia.blog.tag.dao.TagDao;
import com.landaojia.blog.tag.entity.Tag;

@Repository
public class TagDaoImpl extends BaseDao implements TagDao {

    private static final String NAME_SPACE = Tag.class.getName();

    @Override
    public List<Tag> searchTags(List<Tag> tags) {
        return getSqlSession().selectList(NAME_SPACE + ".searchTags", tags);
    }

    @Override
    public void insertTags(List<Tag> tags) {
        getSqlSession().insert(NAME_SPACE + ".insertTags", tags);
    }

    @Override
    public void insertPostTagLink(List<Tag> tags, Long postId) {
        Map<String, Object> param = new HashMap<>();
        param.put("postId", postId);
        param.put("tags", tags);
        getSqlSession().insert(NAME_SPACE + ".insertPostTagLink", param);
    }

    @Override
    public void deletePostTagLink(List<Tag> tags, Long postId) {
        Map<String, Object> param = new HashMap<>();
        param.put("postId", postId);
        param.put("tags", tags);
        getSqlSession().delete(NAME_SPACE + ".deletePostTagLink", param);
    }

    @Override
    public List<Tag> searchPopularTags() {
        return getSqlSession().selectList(NAME_SPACE + ".searchPopularTags");
    }

    @Override
    public void deleteByPostId(Long postId) {
        getSqlSession().delete(NAME_SPACE + ".deleteByPostId", postId);
    }
}
