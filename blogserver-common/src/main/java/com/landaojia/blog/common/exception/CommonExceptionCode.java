package com.landaojia.blog.common.exception;

/**
 * 异常代码枚举
 * @author liuxi
 */
public enum CommonExceptionCode {

    ILLEGAL_OPERATION("非法操作"),
    INCORRECT_PASSWORD("密码错误！"),
    USER_NOT_EXISTS("该用户不存在！"),
    USER_IS_EXISTS("用户名已被注册！"),
    USER_IS_LOGINED("请勿重复登录！"),
    USER_NOT_LOGIN("用户未登录！"),
    POST_NOT_EXISTS("该博客不存在！"),
    POST_ATTACHMENT_UPLOAD_FAIL("附件上传失败！"),
    POST_ATTACHMENT_OUT_OF_SIZE("文件大小超过限制！"),
    POST_ATTACHMENT_NO_RIGHT_UPLOAD("您无权上传该博客的附件！"),
    POST_COMMENT_NOT_EXISTS("该评论不存在！"),
    POST_COMMENT_NO_RIGHT_DELETE("您无权删除该评论！"),
    POST_COMMENT_NO_RIGHT_UPDATE("您无权修改该评论！"),
    POST_NO_AUTH("没有操作权限！"),
    TAG_TOO_LONG("标签超过长度限制"),
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