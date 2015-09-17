package com.landaojia.test.post;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.post.service.PostService;
import com.landaojia.blog.threadlocal.UserThreadLocal;
import com.landaojia.blog.user.entity.User;
import com.landaojia.test.AbstractJunitContextTests;

public class PostServiceTest extends AbstractJunitContextTests {
     
    @Resource
    private PostService postService;
    
    @Resource
    private CommonDao commonDao;
    
    @Before
    public void setUp(){
        User user = this.commonDao.findById(User.class, 1L);
        UserThreadLocal.set(user);
    }
    
    @Test
    public void testCreate() {
        Post post = new Post();
        post.setTitle("test");
        post.setContent("test");
        this.postService.create(post);
    }

    @Test
    public void testQueryAll() {
        System.out.println(this.postService.queryAll(1, 10));
    }

    @Test
    public void testQueryById() {
        System.out.println(this.postService.queryById(1L));
    }

    @Test
    public void testUpdate() {
        Post post = new Post();
        post.setTitle("test");
        post.setContent("test");
        this.postService.update(1L, post);
    }

    @Test
    public void testDelete() {
        this.postService.delete(1L);
    }

}
