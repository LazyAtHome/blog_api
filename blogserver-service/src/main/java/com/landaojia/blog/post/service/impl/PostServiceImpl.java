package com.landaojia.blog.post.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.base.Strings;
import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.common.redis.RedisService;
import com.landaojia.blog.common.util.DateUtil;
import com.landaojia.blog.post.dao.PostDao;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.post.service.PostService;
import com.landaojia.blog.tag.service.TagService;
import com.landaojia.blog.user.entity.User;

@Service
public class PostServiceImpl implements PostService {

    private static final String REDIS_KEY = "viewers_of_post_";

    @Resource
    private CommonDao commonDao;

    @Resource
    private PostDao postDao;

    @Resource
    private RedisService redisService;

    @Resource
    private TagService tagService;

    @Transactional
    @Override
    public void create(Post post, User user) {
        post.setUserId(user.getId());
        post.setViewCount(0L);
        post.setCreatedBy(user.getEmail());
        post.setUpdatedBy(user.getEmail());
        post.setIsPrivate((null != post.getIsPrivate() && post.getIsPrivate()) ? true : false);
        this.commonDao.insert(post);
        this.tagService.saveTags(post.getTagsList(), post.getId());
    }

    @Override
    public Post queryById(Long id, User user) {
        Long userId = null == user ? null : user.getId();
        return this.postDao.selectOne(id, userId);
    }

    @Override
    public Map<String, Object> queryAll(Integer page, Integer limit) {
        PageList<Post> pageList = (PageList<Post>) this.postDao.searchByPage(new Post(), new PageBounds(page,
                limit, Order.formString("createdDate.desc")));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("posts", new ArrayList<Post>(pageList));
        map.put("pageInfo", pageList.getPaginator());
        map.put("popularTags", this.tagService.searchPopularTags());
        return map;
    }

    @Override
    public Map<String, Object> queryByUserId(Integer page, Integer limit, User user) {
        Post cond = new Post();
        cond.setUserId(user.getId());
        PageList<Post> pageList = (PageList<Post>) this.postDao.searchByPage(cond, new PageBounds(page,
                limit, Order.formString("createdDate.desc")));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("posts", new ArrayList<Post>(pageList));
        map.put("pageInfo", pageList.getPaginator());
        return map;
    }

    @Transactional
    @Override
    public void update(Long id, Post post, User user) {
        Post oldPost = this.postDao.selectOne(id, user.getId());
        if (null == oldPost) {
            throw new CommonException(CommonExceptionCode.POST_NOT_EXISTS);
        }
        if (!Strings.isNullOrEmpty(post.getTitle())) {
            oldPost.setTitle(post.getTitle());
        }
        if (!Strings.isNullOrEmpty(post.getContent())) {
            oldPost.setContent(post.getContent());
        }
        oldPost.setIsPrivate((null != post.getIsPrivate() && post.getIsPrivate()) ? true : false);
        oldPost.setUpdatedBy(user.getEmail());
        this.commonDao.update(oldPost);
        this.tagService.updateTags(oldPost.getTagsList(), post.getTagsList(), id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        this.commonDao.removeById(Post.class, id);
        this.tagService.deleteByPostId(id);
    }

    @Transactional
    @Override
    public void addViewCount(String ip, Long id) {
        String key = REDIS_KEY + id;
        if (!this.redisService.exists(key)) {
            this.redisService.sadd(key, ip);
            this.redisService.expire(key, DateUtil.getRemainingSeconds());
            this.postDao.addViewCount(id);
        } else if (!this.redisService.sismember(key, ip)) {
            this.redisService.sadd(key, ip);
            this.postDao.addViewCount(id);
        }
    }

    @Override
    public Map<String, Object> search(Integer page, Integer limit, String by, String q) {
        PageList<Post> pageList = null;
        if (by.equals("tag")) {
            pageList = (PageList<Post>) this.postDao.searchByTag(q,
                    new PageBounds(page, limit, Order.formString("createdDate.desc")));
        } else {
            Post cond = new Post();
            if (by.equals("title")) {
                cond.setTitle(q);
            } else {
                cond.setContent(q);
            }
            pageList = (PageList<Post>) this.postDao.searchByPage(cond,
                    new PageBounds(page, limit, Order.formString("createdDate.desc")));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("posts", new ArrayList<Post>(pageList));
        map.put("pageInfo", pageList.getPaginator());
        return map;
    }

}
