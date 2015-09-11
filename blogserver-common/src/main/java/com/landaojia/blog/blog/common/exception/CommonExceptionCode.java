package com.landaojia.blog.blog.common.exception;

/**
 * 异常代码枚举
 * @author liuxi
 */
public enum CommonExceptionCode {

    INCORRECT_PASSWORD("密码错误！"),
    USER_NOT_EXISTS("该用户不存在！"),
    USER_IS_EXISTS("用户名已被注册！"),
    E999999("系统异常");


    /**
     * 消息
     */
    private String message;

    private CommonExceptionCode( String message ){
        this.message = message;
    }

    public String getCode(){
        return this.name();
    }

    public String getMessage(){
        return message;
    }
}