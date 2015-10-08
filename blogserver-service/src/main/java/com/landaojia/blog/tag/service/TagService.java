package com.landaojia.blog.tag.service;

import java.util.List;

import com.landaojia.blog.tag.entity.Tag;

public interface TagService {

    public void saveTags(List<Tag> tags, Long postId);

    public void updateTags(List<Tag> oldTags, List<Tag> newTags, Long postId);

    public List<Tag> searchPopularTags();

}
