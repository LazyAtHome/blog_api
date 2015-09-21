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
import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.post.dao.PostDao;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.post.service.PostService;
import com.landaojia.blog.user.entity.User;

@Service
public class PostServiceImpl implements PostService {
    
    @Resource
    private CommonDao commonDao;

    @Resource
    private PostDao postDao;

    @Transactional
    @Override
    public void create(Post post, User user) {
        post.setUserId(user.getId());
        post.setViewCount(0L);
        post.setCreatedBy(user.getEmail());
        post.setUpdatedBy(user.getEmail());
        this.commonDao.insert(post);
    }

    @Override
    public Map<String, Object> queryAll(Integer page, Integer limit) {
        PageList<Post> pageList =(PageList<Post>) this.postDao.searchByPage(new Post(), new PageBounds(page, limit, Order.formString("createdDate.desc")));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("posts", new ArrayList<Post>(pageList));
        map.put("pageInfo", pageList.getPaginator());
        return map;
    }

    @Override
    public Post queryById(Long id) {
        return this.commonDao.findById(Post.class, id);
    }

    @Transactional
    @Override
    public void update(Long id, Post post, User user) {
        Post oldPost = this.commonDao.findById(Post.class, id);
        if(null == oldPost) {
            throw new CommonException(CommonExceptionCode.POST_NOT_EXISTS);
        }
        oldPost.setTitle(post.getTitle());
        oldPost.setContent(post.getContent());
        oldPost.setUpdatedBy(user.getEmail());
        this.commonDao.update(oldPost);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        this.commonDao.removeById(Post.class, id);
    }

}
