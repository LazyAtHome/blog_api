package com.landaojia.blog.common.codehelper.ormbuilder;


import com.landaojia.blog.common.util.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * @author codingwoo <long1795@gmail.com>
 */
public class Column {
	private String colName;
	private String javaType;
	private String jdbcType;
	private String fieldName;
	private String getterName;
	private String setterName;
	private String comment;
	private boolean needInCond = false;

	static final Map<String, String> jdbcTypeMap = new HashMap<String, String>();
	static {
		jdbcTypeMap.put("INT", "INTEGER");
		jdbcTypeMap.put("DATETIME", "DATE");
	}

	/**
	 * @param colName
	 * @param javaType
	 * @param jdbcType
	 * @param comment
	 */
	public Column(String colName, String javaType, String jdbcType, String comment, boolean needInCols) {
		this.colName = colName;
		if (jdbcTypeMap.containsKey(jdbcType)) {
			this.jdbcType = jdbcTypeMap.get(jdbcType);
		} else {
			this.jdbcType = jdbcType;
		}
		this.javaType = javaType.replace("java.lang.", "");
		this.comment = comment;
		this.fieldName = Strings.camelName(colName);
		this.getterName = Strings.camelName("get_" + fieldName);
		this.setterName = Strings.camelName("set_" + fieldName);
		this.needInCond = needInCols;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getGetterName() {
		return getterName;
	}

	public void setGetterName(String getterName) {
		this.getterName = getterName;
	}

	public String getSetterName() {
		return setterName;
	}

	public void setSetterName(String setterName) {
		this.setterName = setterName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public boolean isNeedInCond() {
		return needInCond;
	}

	public void setNeedInCond(boolean needInCond) {
		this.needInCond = needInCond;
	}

}
