package business.sys.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.sys.entity.User;
import business.sys.service.UserService;
import core.base.CommonRepository;
import core.base.exception.AppException;
import core.base.mvc.Validator;

@Service
public class UserServiceImpl implements UserService {
    
    @Resource
    CommonRepository commonRepository;
    
    /* (non-Javadoc)
     * @see business.sys.service.impl.UserService#login(java.lang.String, java.lang.String)
     */
    @Override
    @SuppressWarnings("serial")
    public User login(String userName, String password){
        List<User> users = commonRepository.searchByMap(User.class, new HashMap<String, Object>(){{
            put("userName", userName);
            put("password", password);
        }});
        if(users.size() == 1){
            User u = users.get(0);
            if(u.getIsLogin().equals(User.ISLOGIN_LOGOUT)){
                u.setIsLogin(User.ISLOGIN_LOGIN);
                commonRepository.update(u);
                return u;
            } else throw new AppException("用户已经登录");
        }
        throw new AppException("登录失败");
    }
    
    /* (non-Javadoc)
     * @see business.sys.service.impl.UserService#logout(java.lang.Long)
     */
    @Override
    public void logout(Long id){
        if(id == null) throw new AppException("注销失败");
        User user = commonRepository.get(User.class, id);
        if(user == null) throw new AppException("注销失败");
        if(user.getIsLogin().equals(User.ISLOGIN_LOGIN)){
            user.setIsLogin(User.ISLOGIN_LOGOUT);
            commonRepository.update(user);
        } else {
            throw new AppException("用户已经注销登录");
        }
    }

    @Override
    public User register(User user) {
        new Validator(user)
        .forProperty("userName").notNull().hasNoChineseWord().isEmail().length(7, 20)
        .forProperty("password").notNull().hasNoChineseWord().length(7, 15)
        .checkOut();
        user.setIsLogin(User.ISLOGIN_LOGOUT);
        return commonRepository.create(user);
    }
    
}
