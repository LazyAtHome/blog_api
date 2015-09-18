package com.landaojia.blog.user.service;

import com.landaojia.blog.user.entity.User;

/**
 * Created by JUN on 15/9/8.
 */

public interface UserService {

     void registerUser(User user);
     
     User login(String userName, String password);
     
     void logout(Long uerId);
}
