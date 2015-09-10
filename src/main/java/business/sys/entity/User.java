package business.sys.entity;

import core.base.BaseEntity;

/**
 * 
 * @author Jason
 *
 * 2015年9月8日
 */
public class User extends BaseEntity {
    
    //constants start
    public static final Integer ISLOGIN_LOGIN = 1;
    
    public static final Integer ISLOGIN_LOGOUT = 2;
    
    private static final long serialVersionUID = 1L;
    //constants end

    private String  userName;
    private String  password;
    private Integer gender;
    private String  address;
    private Integer isLogin;//whether user has login on this platform 0 stand for no, 1 stand for yes
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Integer getGender() {
        return gender;
    }
    
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    
    public Integer getIsLogin() {
        return isLogin;
    }

    
    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

}
