package com.landaojia.mvc;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.role.UserRole;
import com.landaojia.blog.user.dao.UserDao;
import com.landaojia.blog.user.entity.User;

/**
 * Just for getting current login user,and some other informations about this user;
 * @author Jason
 *
 * 2015年9月9日
 */
public class Current {
    
    public static final String SESSION_LOGIN = "session-login";
    
    CommonDao commonDao;
    
    UserDao userDao;
    
    Long userId;

    public Current(CommonDao commonDao, UserDao userDao, Long userId) {
        super();
        this.commonDao = commonDao;
        this.userDao = userDao;
        this.userId = userId;
    }
    
    public User getCurrentUser(){
        return userId == null ? null:commonDao.findById(User.class, userId);
    }
    
    public UserRole getCurrentUserRole(){
       User user = userId == null ? null : commonDao.findById(User.class, userId);
       if(user != null)
          return UserRole.getRole(user.getRole());
       return null;  
    }
}
