package com.landaojia.blog.post.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.common.validation.Validator;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.threadlocal.UserThreadLocal;
import com.landaojia.blog.user.entity.User;

@Service
public class PostServiceImpl implements PostService {

    @Resource
    private CommonDao commonDao;

    @Transactional
    @Override
    public void create(Post post) {
        validate(post);
        User user = UserThreadLocal.get();
        post.setUserId(user.getId());
        post.setViewCount(0L);
        post.setCreatedBy(user.getEmail());
        post.setUpdatedBy(user.getEmail());
        this.commonDao.insert(post);
    }

    @Override
    public List<Post> queryAll() {
        return this.commonDao.search(new Post());
    }

    @Override
    public Post queryById(Long id) {
        return this.commonDao.findById(Post.class, id);
    }

    @Transactional
    @Override
    public void update(Long id, Post post) {
        validate(post);
        User user = UserThreadLocal.get();
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

    private void validate(Post post) {
        Validator v = new Validator(post);
        v.forProperty("title").notNull().maxLength(50);
        v.forProperty("content").notNull().maxLength(500);
        v.check();
    }

}
