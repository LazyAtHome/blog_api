package core.base.exception;

import java.util.Map;

/**
 * object validateException
 * @author Jason
 *
 * 2015年9月9日
 */
public class ValidateException extends AppException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Map<String, String> errors;

    public ValidateException(Map<String, String> errors) {
        super("校验失败");
        this.errors = errors;
    }
    
    public ValidateException(String msg) {
        super(msg);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
