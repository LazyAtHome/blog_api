package com.landaojia.blog.tag.dao;

import java.util.List;

import com.landaojia.blog.tag.entity.Tag;

public interface TagDao {

    public List<Tag> searchTags(List<Tag> tags);

    public void insertTags(List<Tag> tags);

    public void insertPostTagLink(List<Tag> tags, Long postId);

    public void deletePostTagLink(List<Tag> tags, Long postId);

    public List<Tag> searchPopularTags();

}
