package com.landaojia.test.post;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.post.service.PostService;
import com.landaojia.blog.user.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("classpath*:META-INF/spring/*.xml")
public class PostServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
     
    private MockHttpSession session;
    
    @Resource
    private UserService userService;
    
    @Resource
    private PostService postService;
    
    @Before
    public void setUp(){
        session = new MockHttpSession();
        this.userService.login("jason_lee", "landaojiaTEST", session);
    }
    
    @Test
    public void testCreate() {
        Post post = new Post();
        post.setTitle("test");
        post.setContent("test");
        this.postService.create(post, session);
    }

    @Test
    public void testQueryAll() {
        System.out.println(this.postService.queryAll());
    }

    @Test
    public void testQueryById() {
        System.out.println(this.postService.queryById(2L));
    }

    @Test
    public void testUpdate() {
        Post post = new Post();
        post.setTitle("test");
        post.setContent("test");
        this.postService.update(2L, post, session);
    }

    @Test
    public void testDelete() {
        this.postService.delete(2L, session);
    }

}
