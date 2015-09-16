package com.landaojia.test.common;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.dao.Pagination;
import com.landaojia.blog.user.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/blogserver-dao.xml","classpath:META-INF/spring/blogserver-web-config.xml", "classpath:META-INF/spring/blogserver-context.xml"})
public class CommonDaoTest extends AbstractJUnit4SpringContextTests {
    @Resource
    CommonDao commonDao;
    
    @Test
    public void paginationTest(){
        User user = new User("Jason_Lee");
        Pagination<User> pag = commonDao.searchByPage(new Pagination<User>(user));
        System.out.println(pag.toString());
    }
}
