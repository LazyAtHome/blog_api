package com.landaojia.blog.common.validation;

import java.util.Map;

import com.landaojia.blog.common.exception.CommonException;

public class ValidateException extends CommonException {

	private static final long serialVersionUID = 1L;

	private Map<String, String> errors;

	public ValidateException(Map<String, String> errors) {
		super("未通过数据校验");
		this.errors = errors;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
