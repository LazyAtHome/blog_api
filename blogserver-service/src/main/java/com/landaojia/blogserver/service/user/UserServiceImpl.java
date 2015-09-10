package com.landaojia.blogserver.service.user;

import com.landaojia.blogserver.dao.user.User;
import com.landaojia.blogserver.dao.user.UserDao;
import com.landaojia.blogserver.common.exception.CommonException;
import com.landaojia.blogserver.common.exception.CommonExceptionCode;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * Created by JUN on 15/9/9.
 */
@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserDao userDao;

    public void registerUser(String uname,String pwd) {
        User user=new User();
        user.setUname(uname);
        User u=userDao.selectOne(user);
        if(u!=null){
            throw new CommonException(CommonExceptionCode.USER_IS_EXISTS);
        }
        user.setPwd(pwd);
        userDao.insert(user);
    }


}
