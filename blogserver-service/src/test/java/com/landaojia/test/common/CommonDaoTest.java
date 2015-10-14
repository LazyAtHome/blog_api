package com.landaojia.test.common;

import static org.junit.Assert.assertFalse;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.dao.Pagination;
import com.landaojia.blog.user.entity.User;
import com.landaojia.test.AbstractJunitContextTests;

@Transactional(propagation= Propagation.NOT_SUPPORTED)
public class CommonDaoTest extends AbstractJunitContextTests {
    @Resource
    CommonDao commonDao;
    
    @Test
    public void paginationTest(){
        User user = new User("Jason_Lee");
        Pagination<User> pag = commonDao.searchByPage(new Pagination<User>(user));
        System.out.println(pag.toString());
    }
    
    @Test
    public void pagination2Test(){
        User user = new User("Jason_Lee");
        Pagination<User> pag = commonDao.searchByPage(new Pagination<User>(user, 2, 20));
        System.out.println(pag.toString());
    }
    
    @Test
    public void findByIdTest(){
        assertFalse("失败", commonDao.findById(User.class, 1L) == null);
    }
}
