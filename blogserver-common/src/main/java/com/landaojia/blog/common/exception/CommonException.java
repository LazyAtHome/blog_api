package com.landaojia.blog.common.exception;

/**
 * 通用异常
 * @author liuxi
 */
public class CommonException extends RuntimeException{
	
	private static final long serialVersionUID = -2409606069171261066L;

	/**
	 * 异常代码
	 */
	private String code;
	
	/**
	 * 异常消息
	 */
	private String message;
	
	/**
	 * 抛出该异常的代码点信息：类，方法，行数
	 */
	private String throwAt;
	
	public CommonException( CommonExceptionCode c ){
		this.code = c.getCode();
		this.message = c.getMessage();
		this.throwAt = getThreadStackTrace();
	}
	
	public CommonException( CommonExceptionCode c, Object... params ){
		this.code = c.getCode();
		String message =c.getMessage();
		if( params != null ){
			for( int i = 0; i < params.length; i ++ ){
				message = message.replaceFirst("\\{\\}", String.valueOf( params[i] ) );
			}
		}
		this.message = message;
		this.throwAt = getThreadStackTrace();
	}
	
	public CommonException( String message ){
		this.code = CommonExceptionCode.E999999.getCode();
		this.message = message;
		this.throwAt = getThreadStackTrace();
	}
	
	/**
	 * 获取踪迹信息
	 */
	private String getThreadStackTrace() {
		StackTraceElement[] stes = Thread.currentThread().getStackTrace();
		StackTraceElement tgt = stes[3];
		return tgt.getClassName() + "[" + tgt.getMethodName() + "(...):" + tgt.getLineNumber() + "]";
	}
	
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getThrowAt() {
		return throwAt;
	}
}
