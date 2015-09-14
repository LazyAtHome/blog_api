package com.landaojia.blog.common.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class Validator {

	private StandardEvaluationContext sec;
	private ExpressionParser parser;

	private Map<String, String> errors;

	private String name;
	private Object value;

	public Validator(Object target) {
		this.errors = new HashMap<String, String>();
		sec = new StandardEvaluationContext(target);
		parser = new SpelExpressionParser();
	}

	public Validator forProperty(String name) {
		this.name = name;
		this.value = parser.parseExpression(name).getValue(sec);
		return this;
	}

	public Validator check() {
		if (errors.size() == 0) {
			return this;
		} else {
			throw new ValidateException(errors);
		}
	}

	private boolean error() {
		return errors.get(name) != null;
	}

	/*****************************************************************************/

	public Validator notNull() {
		if (!error() && (value == null)) {
			errors.put(name, "不能为空");
		}
		return this;
	}

	public Validator notBlank() {
		if (!error() && (value != null) && ((String) value).trim().length() == 0) {
			errors.put(name, "不能为空");
		}
		return this;
	}

	public Validator minLength(int min) {
		if (!error() && (value != null) && !GenericValidator.minLength((String) value, min)) {
			errors.put(name, "最小长度" + min);
		}
		return this;
	}

	public Validator maxLength(int max) {
		if (!error() && (value != null) && !GenericValidator.maxLength((String) value, max)) {
			errors.put(name, "最大长度" + max);
		}
		return this;
	}

	public Validator length(int min, int max) {
		if (!error() && (value != null) && (!GenericValidator.minLength((String) value, min) || !GenericValidator.maxLength((String) value, max))) {
			errors.put(name, "长度" + min + "到" + max);
		}
		return this;
	}

	public Validator isInteger() {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !GenericValidator.isInt((String) value)) {
			errors.put(name, "必须为整数");
		}
		return this;
	}

	public Validator isFloat() {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !GenericValidator.isFloat((String) value)) {
			errors.put(name, "必须为小数");
		}
		return this;
	}

	public Validator isDouble() {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !GenericValidator.isDouble((String) value)) {
			errors.put(name, "必须为小数");
		}
		return this;
	}

	public Validator isInRange(int min, int max) {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !GenericValidator.isInRange(Integer.valueOf((String) value), min, max)) {
			errors.put(name, "数值" + min + "到" + max);
		}
		return this;
	}

	public Validator isInRange(float min, float max) {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !GenericValidator.isInRange(Float.valueOf((String) value), min, max)) {
			errors.put(name, "数值" + min + "到" + max);
		}
		return this;
	}

	public Validator isInRange(double min, double max) {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !GenericValidator.isInRange(Double.valueOf((String) value), min, max)) {
			errors.put(name, "数值" + min + "到" + max);
		}
		return this;
	}

	public Validator isEmail() {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !GenericValidator.isEmail((String) value)) {
			errors.put(name, "格式错误");
		}
		return this;
	}

	public Validator isDate() {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !GenericValidator.isDate((String) value, "yyyy-MM-dd", false)) {
			errors.put(name, "日期格式错误");
		}
		return this;
	}

	public Validator isDate(String datePattern) {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !GenericValidator.isDate((String) value, datePattern, false)) {
			errors.put(name, "日期格式错误");
		}
		return this;
	}

	public Validator isUrl() {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !GenericValidator.isUrl((String) value)) {
			errors.put(name, "url格式错误");
		}
		return this;
	}

	public Validator hasNoChineseWord() {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && Pattern.compile("[\\u4e00-\\u9fa5]").matcher((String) value).find()) {
			errors.put(name, "不能包含中文");
		}
		return this;
	}

	public Validator isMobileInChina() {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$").matcher((String) value).matches()) {
			errors.put(name, "不正确的手机号");
		}
		return this;
	}

	public Validator isPhoneInChina() {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !Pattern.compile("^([0-9]{3}-?[0-9]{8})|([0-9]{4}-?[0-9]{7})$").matcher((String) value).matches()) {
			errors.put(name, "不正确的电话号码");
		}
		return this;
	}

	public Validator isFaxInChina() {
		if (!error() && !Pattern.compile("^([0-9]{3}-?[0-9]{8})|([0-9]{4}-?[0-9]{7})$").matcher((String) value).matches()) {
			errors.put(name, "不正确的传真号码");
		}
		return this;
	}

	public Validator isIdCard() {
		if (!error() && !Pattern.compile("^([0-9]{15})|([0-9]{18})|([0-9]{17}(x|X))$").matcher((String) value).matches()) {
			errors.put(name, "不正确的身份证号码");
		}
		return this;
	}

	public Validator isPostCode() {
		if (!error() && !Pattern.compile("^([0-9]{6})$").matcher((String) value).matches()) {
			errors.put(name, "不正确的邮政编码");
		}
		return this;
	}

	public Validator isIp() {
		if (!error() && (value != null) && (!((String) value).trim().equals("")) && !InetAddressValidator.getInstance().isValid((String) value)) {
			errors.put(name, "格式错误");
		}
		return this;
	}

	public Validator custom(boolean positive, String msg) {
		if (!error() && !positive) {
			errors.put(name, msg);
		}
		return this;
	}

}
