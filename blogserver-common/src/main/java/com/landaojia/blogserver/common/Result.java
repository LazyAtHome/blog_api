package com.landaojia.blogserver.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象结果类
 * 
 * @author Gray <b>long1795@gmail.com</b>
 */
public class Result extends BaseObject {
	private static final long serialVersionUID = 1L;


	private static final Map<Integer, String> map = new HashMap<Integer, String>();
	/**
	 * 应答码 - 成功
	 */
	public static final int RESP_CODE_SUCC = 0;

	/**
	 * 应答码 - 用户不存在
	 */
	public static final int RESP_CODE_ERROR_USER_NOT_EXISTS = 8999;

	/**
	 * 应答码 - 未知错误
	 */
	public static final int RESP_CODE_ERROR_UNKNOWN = 9999;

	static {
		map.put(RESP_CODE_SUCC, "success");
		map.put(RESP_CODE_ERROR_USER_NOT_EXISTS, "user not exists");
		map.put(RESP_CODE_ERROR_UNKNOWN, "unknown error");
	}

	public Boolean isSucc() {
		return resultCode != null && RESP_CODE_SUCC == resultCode;
	}

	public static <T extends Result> T succ(Class<T> clazz) {
		try {
			T t = clazz.newInstance();
			t.setResultCode(RESP_CODE_SUCC);
			t.setResultMsg(map.get(RESP_CODE_SUCC));
			return t;
		} catch (Exception e) {
			//T 为 Result 子类，必须有一个无参数的public构造方法。因此此处不考虑异常
			return null;
		}
	}

	public static <T extends Result> T fail(Class<T> clazz) {
		return fail(clazz, RESP_CODE_ERROR_UNKNOWN, map.get(RESP_CODE_ERROR_UNKNOWN));
	}

	public static <T extends Result> T failUserNotExists(Class<T> clazz) {
		return fail(clazz, RESP_CODE_ERROR_USER_NOT_EXISTS, map.get(RESP_CODE_ERROR_USER_NOT_EXISTS));
	}

	public static <T extends Result> T fail(Class<T> clazz, int code, String msg) {
		try {
			T t = clazz.newInstance();
			t.setResultCode(code);
			t.setResultMsg(msg);
			return t;
		} catch (Exception e) {
			//T 为 Result 子类，必须有一个无参数的public构造方法。因此此处不考虑异常
			return null;
		}
	}

	/**
	 * 响应码
	 */
	private Integer resultCode;//响应码

	/**
	 * 响应消息
	 */
	private String resultMsg;//响应消息

	/**
	 * @return 响应码
	 */
	public Integer getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode
	 *            响应码
	 */
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return 响应消息
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param resultMsg
	 *            响应消息
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}
