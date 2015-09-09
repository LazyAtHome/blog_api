package business.sys.service;

import business.sys.entity.User;

public interface UserService {
    /**
     * login using username and password
     * @param userName
     * @param password
     * @return
     */
    User login(String userName, String password);

    /**
     * logout using the id of user;
     * @param id
     */
    void logout(Long id);
    
    /**
     * register user
     * @param id
     * @return
     */
    User register(User user);

}
