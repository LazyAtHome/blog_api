package com.landaojia.blog.post.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.common.validation.Validator;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.user.entity.User;
import com.landaojia.mvc.Current;

@Service
public class PostServiceImpl implements PostService {

    @Resource
    private CommonDao commonDao;

    @Transactional
    @Override
    public void create(Post post, HttpSession session) {
        User user = checkLogin(session);
        validate(post);
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
        Post template = new Post();
        template.setId(id);
        List<Post> list = this.commonDao.search(template);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Transactional
    @Override
    public void update(Long id, Post post, HttpSession session) {
        User user = checkLogin(session);
        Post oldPost = checkAuth(id, user);
        validate(post);
        oldPost.setTitle(post.getTitle());
        oldPost.setContent(post.getContent());
        oldPost.setUpdatedBy(user.getEmail());
        this.commonDao.update(oldPost);
    }

    @Transactional
    @Override
    public void delete(Long id, HttpSession session) {
        User user = checkLogin(session);
        checkAuth(id, user);
        this.commonDao.removeById(Post.class, id);
    }

    private void validate(Post post) {
        Validator v = new Validator(post);
        v.forProperty("title").notNull().maxLength(50);
        v.forProperty("content").notNull().maxLength(500);
        v.check();
    }

    private User checkLogin(HttpSession session) {
        User user = (User) session.getAttribute(Current.SESSION_LOGIN);
        if (null == user) {
            throw new CommonException(CommonExceptionCode.USER_NOT_LOGIN);
        }
        return user;
    }

    private Post checkAuth(Long id, User user) {
        Post template = new Post();
        template.setId(id);
        List<Post> list = this.commonDao.search(template);
        Post post = list.size() > 0 ? list.get(0) : null;
        if (null == post) {
            throw new CommonException(CommonExceptionCode.POST_NOT_EXISTS);
        }
        if (!(post.getUserId().equals(user.getId()) || StringUtils.equals("admin", user.getRole()))) {
            throw new CommonException(CommonExceptionCode.POST_NO_AUTH);
        }
        return post;
    }

}
