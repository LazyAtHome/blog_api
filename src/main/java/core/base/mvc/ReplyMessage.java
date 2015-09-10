package core.base.mvc;

import core.base.exception.AppException;
import core.base.utils.StringUtil;

/**
 * Default http response data pattern
 * 
 * @author Jason 2015年9月9日
 */
public class ReplyMessage {

    private String message;

    private Object returnObject;

    private boolean isSuccess;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public static ReplyMessage success() {
        ReplyMessage rep = new ReplyMessage();
        rep.setSuccess(true);
        return rep;
    }

    public static ReplyMessage fail() {
        ReplyMessage rep = new ReplyMessage();
        rep.setSuccess(false);
        return rep;
    }

    public ReplyMessage message(String message) {
        if (StringUtil.isNullOrEmpty(message)) throw new AppException("操作失败");
        this.setMessage(message);
        return this;
    }

    public ReplyMessage returObject(Object ro) {
        if (ro == null) throw new AppException("操作失败");
        this.setReturnObject(ro);
        ;
        return this;
    }
}
