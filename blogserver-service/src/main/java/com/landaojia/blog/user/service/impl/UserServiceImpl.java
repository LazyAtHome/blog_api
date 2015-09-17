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
import com.landaojia.blog.common.validation.Validator;
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
    public void registerUser(User user) {
        new Validator(user)
        .forProperty("userName").notNull().notBlank().length(7, 20).hasNoChineseWord()
        .forProperty("email").notNull().maxLength(100).isEmail().hasNoChineseWord().custom(commonDao.search(new User().setEmail(user.getEmail())).size() == 0, "该邮箱地址已被注册")
        .forProperty("cryptedPasswordConfirm").notNull().notBlank().length(7, 20)
        .forProperty("cryptedPassword").notNull().notBlank().length(7, 20).custom(user.getCryptedPasswordConfirm() == null ? false : user.getCryptedPassword().equals(user.getCryptedPasswordConfirm()), "两次输入密码不一致").check();
        if(commonDao.search(new User(user.getUserName())).size() > 0){
            throw new CommonException(CommonExceptionCode.USER_IS_EXISTS);
        } else {
            user.setCreatedDate(DateUtil.getCurrentDate());
            user.setCreatedBy("sys");
            user.setUpdatedBy("sys");
            user.setUpdatedDate(DateUtil.getCurrentDate());
            user.setRole("admin");
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
        return user;
    }

    @Override
    public void logout(Long userId) {
        //TODO other businesses in service layer
    }

}
