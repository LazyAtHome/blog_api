package com.landaojia.test.post;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.post.service.PostService;
import com.landaojia.blog.threadlocal.UserThreadLocal;
import com.landaojia.blog.user.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:META-INF/spring/blogserver-dao.xml","classpath:META-INF/spring/blogserver-web-config.xml", "classpath:META-INF/spring/blogserver-context.xml"})
public class PostServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
     
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
        System.out.println(this.postService.queryAll());
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
