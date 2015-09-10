package core.base.exception;

/**
 * Basic Exception on the platform;
 * @author Jason
 *
 * 2015年9月8日
 */
public class AppException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    
    /**
     * Standard tip information
     */
    private String  message = "操作失败";
    
    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable throwable) {
        super(throwable);
    }
    
    public AppException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
