package com.landaojia.blog.tag.dao;

import java.util.List;
import java.util.Set;

import com.landaojia.blog.tag.entity.Tag;

public interface TagDao {

    public List<Tag> searchTags(Set<Tag> tags);

    public void insertTags(Set<Tag> tags);

    public void insertPostTagLink(Long postId, Set<Tag> tags);

}
