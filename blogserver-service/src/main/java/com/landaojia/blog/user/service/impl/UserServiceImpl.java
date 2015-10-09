package com.landaojia.blog.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.common.util.DateUtil;
import com.landaojia.blog.common.util.EncryptUtil;
import com.landaojia.blog.common.util.Strings;
import com.landaojia.blog.role.UserRole;
import com.landaojia.blog.user.dao.UserDao;
import com.landaojia.blog.user.entity.User;
import com.landaojia.blog.user.service.UserService;

/**
 * Created by JUN on 15/9/9.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private CommonDao commonDao;
    
    @Transactional
    public void register(User user) {
        if(commonDao.search(new User(user.getUserName())).size() > 0){
            throw new CommonException(CommonExceptionCode.USER_IS_EXISTS);
        } else {
            user.setCreatedDate(DateUtil.getCurrentDate());
            user.setCreatedBy("sys");
            user.setUpdatedBy("sys");
            user.setUpdatedDate(DateUtil.getCurrentDate());
            user.setRole(UserRole.GUEST.getValue());
            user.setCryptedPassword(EncryptUtil.encrypt(user.getCryptedPassword()));
            commonDao.insert(user);
        }
    }

    @Override
    public User login(String userName, String password) {
        if(Strings.isNullOrEmpty(userName, password)){
            throw new CommonException(CommonExceptionCode.E999999);
        }
        List<User> users = commonDao.search(new User(userName));
        if(users.size() != 1){
            throw new CommonException(CommonExceptionCode.USER_NOT_EXISTS);
        }
        User user = users.get(0);
        if(!user.getCryptedPassword().equals(EncryptUtil.encrypt(password))){
            throw new CommonException(CommonExceptionCode.INCORRECT_PASSWORD);
        }
        //set accessToken
        user.setAccessToken(EncryptUtil.encrypt(new StringBuilder(user.getUserName()).append(" ").append(user.getId()).toString()));
        user.setCryptedPassword(null);
        return user;
    }

    @Override
    public void logout(Long userId) {
        //TODO other businesses in service layer
    }
    
    @Override
    public void approveUser(Long operId, String operName, Long userId){
        if(operId == null || userId == null || Strings.isNullOrEmpty(operName)) throw new CommonException(CommonExceptionCode.E999999);
        User u = commonDao.findById(User.class, userId);
        if(u == null) throw new CommonException(CommonExceptionCode.USER_NOT_EXISTS);
        if(operId.compareTo(userId) == 0) throw new CommonException(CommonExceptionCode.ILLEGAL_OPERATION);
        u.setRole(UserRole.EDITOR.getValue());
        u.setUpdatedBy(operName);
        u.setUpdatedDate(DateUtil.getCurrentDate());
        commonDao.update(u);
    }

}
