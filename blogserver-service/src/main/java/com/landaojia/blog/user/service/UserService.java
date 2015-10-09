package com.landaojia.blog.user.service;

import com.landaojia.blog.user.entity.User;

/**
 * Created by JUN on 15/9/8.
 */

public interface UserService {

     void register(User user);
     
     User login(String userName, String password);
     
     void logout(Long uerId);
     
     /**
      * Just for ADMIN role to approve register user having GUEST role.
      * @param oper the approver who do this operation. 
      * @param userId the approved user's id.
      */
     void approveUser(Long operId, String operName, Long userId);
}
