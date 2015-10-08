package com.landaojia.blog.tag.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.landaojia.blog.common.dao.BaseDao;
import com.landaojia.blog.tag.dao.TagDao;
import com.landaojia.blog.tag.entity.Tag;

@Repository
public class TagDaoImpl extends BaseDao implements TagDao {

    private static final String NAME_SPACE = Tag.class.getName();

    @Override
    public List<Tag> searchTags(Set<Tag> tags) {
        return getSqlSession().selectList(NAME_SPACE + ".searchTags", new ArrayList<>(tags));
    }

    @Override
    public void insertTags(Set<Tag> tags) {
        getSqlSession().insert(NAME_SPACE + ".insertTags", new ArrayList<>(tags));
    }

    @Override
    public void insertPostTagLink(Long postId, Set<Tag> tags) {
        Map<String, Object> param = new HashMap<>();
        param.put("postId", postId);
        param.put("tags", new ArrayList<>(tags));
        getSqlSession().insert(NAME_SPACE + ".insertPostTagLink", param);
    }
}
