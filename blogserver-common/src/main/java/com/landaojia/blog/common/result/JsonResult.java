package com.landaojia.blog.common.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.landaojia.blog.common.BaseObject;
import com.landaojia.blog.common.Result;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 统一json对象
 * @author liuxi
 */
public class JsonResult extends BaseObject{
	/**
	 * 返回码，1:成功
	 */
	public static final int CODE_SUCCESS = 0;

	/**
	 * 返回码，0:业务或系统异常
	 */
	public static final int CODE_FAIL = 1;

	/**
	 * 返回码, 0:成功, 1:失败
	 */
	private int responseCode;
	
	/**
	 * 返回消息，当responseCode非0时，标识异常消息
	 */
	private String responseMsg;
	
	/**
	 * 结果对象
	 */
	private Object data;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date now;

	public JsonResult(){
		this.now=new Date();
	}
	
	/**
	 * 成功的调用
	 */
	public static JsonResult success(){
		JsonResult ar = new JsonResult();
		ar.responseCode = CODE_SUCCESS;
		ar.data = null;
		return ar;
	}

	/**
	 * 成功的调用
	 */
	public static JsonResult success(Object data){
		JsonResult ar = new JsonResult();
		ar.responseCode = CODE_SUCCESS;
		ar.data = data;
		return ar;
	}
	
	public static JsonResult failure(Object data){
	    JsonResult ar = new JsonResult();
	    ar.responseCode = CODE_FAIL;
	    ar.data = data;
	    return ar;
	}
	
	/**
	 * 失败的调用
	 */
	public static JsonResult failure( String msg ){
		JsonResult ar = new JsonResult();
		ar.responseCode = CODE_FAIL;
		ar.responseMsg = msg;
		return ar;
	}

	/**
	 * 失败的调用
	 */
	public static JsonResult failure(int code, String msg){
		JsonResult ar = new JsonResult();
		ar.responseCode = code;
		ar.responseMsg = msg;
		return ar;
	}
	
	public static JsonResult fromApiJsonResult( Result response, Object ro ){
		if( response.isSucc() ){
			BeanUtils.copyProperties( response, ro );
			return JsonResult.success( ro );
		}else{
			return JsonResult.failure( response.getResultMsg() );
		}
	}
	
	/**
	 * 转成json格式
	 */
	public String toJson(){
		return JSON.toJSONString( this );
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}
}
