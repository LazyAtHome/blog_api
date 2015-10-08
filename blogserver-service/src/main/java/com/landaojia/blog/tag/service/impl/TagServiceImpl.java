package com.landaojia.blog.tag.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.landaojia.blog.tag.dao.TagDao;
import com.landaojia.blog.tag.entity.Tag;
import com.landaojia.blog.tag.service.TagService;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagDao tagDao;

    @Override
    public void saveTags(List<Tag> tags, Long postId) {
        if (null == tags || tags.size() == 0)
            return;
        Set<Tag> tagsExists = new HashSet<>(this.tagDao.searchTags(tags));
        tags.removeAll(tagsExists);
        if (tags.size() > 0) {
            this.tagDao.insertTags(tags);
            tagsExists.addAll(new HashSet<>(this.tagDao.searchTags(tags)));
        }
        this.tagDao.insertPostTagLink(new ArrayList<>(tagsExists), postId);
    }

    @Override
    public void updateTags(List<Tag> oldTags, List<Tag> newTags, Long postId) {
        if (null == newTags)
            return;
        Set<Tag> tags2delete = new HashSet<>(oldTags);
        tags2delete.removeAll(new HashSet<>(newTags));
        if (tags2delete.size() > 0) 
            this.tagDao.deletePostTagLink(new ArrayList<>(tags2delete), postId);
        Set<Tag> tags2save = new HashSet<>(newTags);
        tags2save.removeAll(new HashSet<>(oldTags));
        if (tags2save.size() > 0)
            saveTags(new ArrayList<>(tags2save), postId);
    }

    @Override
    public List<Tag> searchPopularTags() {
        return this.tagDao.searchPopularTags();
    }

    @Override
    public void deleteByPostId(Long postId) {
        this.tagDao.deleteByPostId(postId);
    }

}
