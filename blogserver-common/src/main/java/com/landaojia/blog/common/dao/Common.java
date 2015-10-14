package com.landaojia.blog.common.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface Common {


    <T extends BaseEntity, ID extends Serializable> void removeById(Class<T> clazz, ID id);

    <T extends BaseEntity> void removeOnTondition(T entity);

    <T extends BaseEntity> T insert(T entity);

    <T extends BaseEntity> T insertWithId(T entity);

    <T extends BaseEntity> List<T> batchInsert(List<T> entities);

    <T extends BaseEntity> List<T> batchInsertWithId(List<T> entities);

    <T extends BaseEntity> T update(T entity);

    <T extends BaseEntity> Long countOnTondition(T entity);

    <T extends BaseEntity> BigDecimal sumOnTondition(T entity);

    <T extends BaseEntity> List<T> search(T entity);

}
