package com.landaojia.mvc;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.user.dao.UserDao;
import com.landaojia.blog.user.entity.User;

/**
 * Just for getting current login user,and some other informations about this user;
 * @author Jason
 *
 * 2015年9月9日
 */
public class Current {
    
    CommonDao commonDao;
    
    UserDao userDao;
    
    User user;


    public Current(CommonDao commonDao, UserDao userDao, User user) {
        super();
        this.commonDao = commonDao;
        this.userDao = userDao;
        this.user = user;
    }
    
    public User getCurrentUser(){
        return user;
    }
    
}
