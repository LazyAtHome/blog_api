package com.landaojia.blog.user.service;

import javax.servlet.http.HttpSession;

import com.landaojia.blog.user.entity.User;

/**
 * Created by JUN on 15/9/8.
 */

public interface UserService {

     void registerUser(User user);
     
     void login(String userName, String password, HttpSession session);
     
     void logout(HttpSession session);

}
