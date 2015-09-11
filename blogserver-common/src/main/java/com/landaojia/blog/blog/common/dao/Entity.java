package com.landaojia.blog.blog.common.dao;

import java.io.Serializable;

public interface Entity<ID extends Serializable> {
    
    public ID getId();

    public void setId(ID id);
}
