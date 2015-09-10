package core.base.mvc;

import business.sys.entity.User;
import business.sys.repository.UserRepository;
import core.base.CommonRepository;

/**
 * Just for getting current login user,and some other informations about this user;
 * @author Jason
 *
 * 2015年9月9日
 */
public class Current {
    
    CommonRepository commonRepository;
    
    UserRepository userRepository;
    
    User user;

    /**
     * Inject the spring beans we need by constructor of this object.
     * @param commonRepository
     * @param userRepository
     */
    public Current(CommonRepository commonRepository, UserRepository userRepository, User user) {
        super();
        this.commonRepository = commonRepository;
        this.userRepository = userRepository;
        this.user = user;
    }
    
    public User getCurrentUser(){
        return user;
    }
    
}
