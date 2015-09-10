package com.landaojia.blogserver.dao;


import com.landaojia.blogserver.common.BaseObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Gray <long1795@gmail.com>
 */
public class BaseEO extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Long id;
	transient private List<Long> ids;

	private Date createTime;
	transient private Date createTimeB;
	transient private Date createTimeE;

	private Date updateTime;
	transient private Date updateTimeB;
	transient private Date updateTimeE;

	private Integer version;

	public static Date getDate(int delta) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, delta);
		return c.getTime();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Date getCreateTimeB() {
		return createTimeB;
	}

	public void setCreateTimeB(Date createTimeB) {
		this.createTimeB = createTimeB;
	}

	public Date getCreateTimeE() {
		return createTimeE;
	}

	public void setCreateTimeE(Date createTimeE) {
		this.createTimeE = createTimeE;
	}

	public Date getUpdateTimeB() {
		return updateTimeB;
	}

	public void setUpdateTimeB(Date updateTimeB) {
		this.updateTimeB = updateTimeB;
	}

	public Date getUpdateTimeE() {
		return updateTimeE;
	}

	public void setUpdateTimeE(Date updateTimeE) {
		this.updateTimeE = updateTimeE;
	}

}
