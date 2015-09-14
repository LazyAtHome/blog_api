package com.landaojia.blog.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.user.dao.UserDao;
import com.landaojia.blog.user.entity.User;

/**
 * Created by JUN on 15/9/9.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private CommonDao commonDao;

    public void registerUser(String uname, String pwd) {
        User user = new User();
        user.setUsername(uname);
        List<User> users = commonDao.search(user);
        if(users.size() != 1){
            throw new CommonException(CommonExceptionCode.USER_IS_EXISTS);
        } else {
            User u = users.get(0);
            u.setCryptedPassword(pwd);
            commonDao.insert(u);
        }
    }

}
