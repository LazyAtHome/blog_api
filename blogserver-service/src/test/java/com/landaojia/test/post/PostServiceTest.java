package com.landaojia.test.post;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.post.entity.Post;
import com.landaojia.blog.post.service.PostService;
import com.landaojia.blog.user.entity.User;
import com.landaojia.test.AbstractJunitContextTests;

public class PostServiceTest extends AbstractJunitContextTests {
     
    @Resource
    private PostService postService;
    
    @Resource
    private CommonDao commonDao;
    
    User user;
    
    @Before
    public void setUp(){
        user = this.commonDao.findById(User.class, 1L);
    }
    
    @Test
    public void testCreate() {
        Post post = new Post();
        post.setTitle("test");
        post.setContent("test");
        this.postService.create(post, user);
    }

    @Test
    public void testQueryAll() {
        System.out.println(this.postService.queryAll(1, 10));
    }

    @Test
    public void testQueryById() {
        System.out.println(this.postService.queryById(1L, user));
    }

    @Test
    public void testUpdate() {
        Post post = new Post();
        post.setTitle("test");
        post.setContent("test");
        this.postService.update(1L, post, user);
    }

    @Test
    public void testDelete() {
        this.postService.delete(1L);
    }
    
    @Test
    public void testHHH() throws FileNotFoundException, IOException {
    	User user = commonDao.findById(User.class, 41L);
    	MockMultipartFile file = new MockMultipartFile("xxx.docx", new FileInputStream(new File("d:/懒到家用户APP功能需求文档3.0.docx")));
    	this.postService.addAttachment(file, 154L, user, "D:/files/");
    }

}
