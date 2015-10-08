package com.landaojia.blog.tag.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.tag.dao.TagDao;
import com.landaojia.blog.tag.entity.Tag;
import com.landaojia.blog.tag.service.TagService;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagDao tagDao;

    @Override
    public void saveTags(String tagsStr, Long PostId) {
        if (null == tagsStr)
            return;
        Set<Tag> tags = new HashSet<>();
        for (String tag : tagsStr.split(",|，|\\s+")) {
            if (tag.length() > 10)
                throw new CommonException(CommonExceptionCode.TAG_TOO_LONG);
            if (!Strings.isNullOrEmpty(tag))
                tags.add(new Tag(tag));
        }
        Set<Tag> tagsExists = new HashSet<>(this.tagDao.searchTags(tags));
        tags.removeAll(tagsExists);
        if (tags.size() > 0) {
            this.tagDao.insertTags(tags);
            tagsExists.addAll(new HashSet<>(this.tagDao.searchTags(tags)));
        }
        this.tagDao.insertPostTagLink(PostId, tagsExists);
    }

}
