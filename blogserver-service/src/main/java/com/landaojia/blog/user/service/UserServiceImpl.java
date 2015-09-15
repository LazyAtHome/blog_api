package com.landaojia.blog.user.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.landaojia.blog.common.dao.CommonDao;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;
import com.landaojia.blog.common.util.DateUtil;
import com.landaojia.blog.common.util.Strings;
import com.landaojia.blog.common.validation.Validator;
import com.landaojia.blog.user.dao.UserDao;
import com.landaojia.blog.user.entity.User;
import com.landaojia.mvc.Current;

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
        .forProperty("email").notNull().maxLength(100).isEmail().hasNoChineseWord()
        .forProperty("cryptedPassword").notNull().notBlank().length(7, 20).custom(user.getCryptedPassword().equals(user.getCryptedPasswordConfirm()), "两次输入密码不一致").check();
        List<User> users = commonDao.search(new User(user.getUserName()));
        if(users.size() > 0){
            throw new CommonException(CommonExceptionCode.USER_IS_EXISTS);
        } else {
            user.setCreatedDate(DateUtil.getCurrentDate());
            user.setCreatedBy("sys");
            user.setUpdatedBy("sys");
            user.setUpdatedDate(DateUtil.getCurrentDate());
            user.setRole("admin");
            commonDao.insert(user);
        }
    }

    @Override
    public void login(String userName, String password, HttpSession session) {
        if(Strings.isNullOrEmpty(userName, password)){
            throw new CommonException(CommonExceptionCode.E999999);
        }
        List<User> users = commonDao.search(new User(userName));
        User user = users.get(0);
        if(user  == null){
            throw new CommonException(CommonExceptionCode.USER_NOT_EXISTS);
        }
        if(!user.getCryptedPassword().equals(password)){//TODO 加密工具
            throw new CommonException(CommonExceptionCode.INCORRECT_PASSWORD);
        }
        session.setAttribute(Current.SESSION_LOGIN, user);
    }

    @Override
    public void logout(HttpSession session) {
        session.removeAttribute(Current.SESSION_LOGIN);
    }

}
