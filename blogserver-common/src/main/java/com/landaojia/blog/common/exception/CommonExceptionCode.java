package com.landaojia.blog.common.exception;

/**
 * 异常代码枚举
 * @author liuxi
 */
public enum CommonExceptionCode {

    INCORRECT_PASSWORD("密码错误！"),
    USER_NOT_EXISTS("该用户不存在！"),
    USER_IS_EXISTS("用户名已被注册！"),
    USER_IS_LOGINED("请勿重复登录！"),
    USER_NOT_LOGIN("用户未登录！"),
    POST_NOT_EXISTS("该博客不存在！"),
    POST_NO_AUTH("没有操作权限"),
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