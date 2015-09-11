package com.landaojia.blog.blog.common.dao;


import com.landaojia.blog.blog.common.BaseObject;

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

	private Date createDate;
	transient private Date createDateB;
	transient private Date createDateE;

	private Date updateDate;
	transient private Date updateDateB;
	transient private Date updateDateE;

	private Integer deletedFlag;
	private String createBy;
	private String updateBy;

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

	public Date getcreateDate() {
		return createDate;
	}

	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getupdateDate() {
		return updateDate;
	}

	public void setupdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

	public Date getcreateDateB() {
		return createDateB;
	}

	public void setcreateDateB(Date createDateB) {
		this.createDateB = createDateB;
	}

	public Date getcreateDateE() {
		return createDateE;
	}

	public void setcreateDateE(Date createDateE) {
		this.createDateE = createDateE;
	}

	public Date getupdateDateB() {
		return updateDateB;
	}

	public void setupdateDateB(Date updateDateB) {
		this.updateDateB = updateDateB;
	}

	public Date getupdateDateE() {
		return updateDateE;
	}

	public void setupdateDateE(Date updateDateE) {
		this.updateDateE = updateDateE;
	}

	public Integer getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(Integer deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}
