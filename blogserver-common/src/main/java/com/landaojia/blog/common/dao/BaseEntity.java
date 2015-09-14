package com.landaojia.blog.common.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * base entity for every business entity;
 * 
 * @author Jason 2015年9月8日
 */
public abstract class BaseEntity implements Entity<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    // static immutable variables definition---------start----------
    public static final Integer DELETED_FLAG_NO  = 0;
    public static final Integer DELETED_FLAG_YES = 1;
    // static immutable variables definition---------end----------

    private Long    id;
    private Integer version;
    private Integer deletedFlag;
    private Date    createdDate;
    transient private Date    createdDateB;
    transient private Date    createdDateE;
    private Date    updatedDate;
    transient private Date    updatedDateB;
    transient private Date    updatedDateE;
    private String    createdBy;
    private String    updatedBy;

    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Entity)) {
            return false;
        }
        if (this.getClass().equals(obj.getClass())) return ((Entity<Long>) obj).getId().compareTo(this.getId()) == 0;
        return false;
    }

    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : id.hashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(Integer deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    
    public Date getCreatedDateB() {
        return createdDateB;
    }

    
    public void setCreatedDateB(Date createdDateB) {
        this.createdDateB = createdDateB;
    }

    
    public Date getCreatedDateE() {
        return createdDateE;
    }

    
    public void setCreatedDateE(Date createdDateE) {
        this.createdDateE = createdDateE;
    }

    
    public Date getUpdatedDateB() {
        return updatedDateB;
    }

    
    public void setUpdatedDateB(Date updatedDateB) {
        this.updatedDateB = updatedDateB;
    }

    
    public Date getUpdatedDateE() {
        return updatedDateE;
    }

    
    public void setUpdatedDateE(Date updatedDateE) {
        this.updatedDateE = updatedDateE;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

}
