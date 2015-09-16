package com.landaojia.blog.common.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
 * It is used to integrate all of the basic CRUD operations
 * 
 * @author Jason 2015年9月8日
 */
public interface CommonDao {

    <T extends BaseEntity, ID extends Serializable> T findById(Class<T> clazz, ID id);

    <T extends BaseEntity, ID extends Serializable> void removeById(Class<T> clazz, ID id);

    <T extends BaseEntity> void removeOnCondition(T entity);

    <T extends BaseEntity> T insert(T entity);

    <T extends BaseEntity> T insertWithId(T entity);

    <T extends BaseEntity> List<T> batchInsert(List<T> entities);

    <T extends BaseEntity> List<T> batchInsertWithId(List<T> entities);

    <T extends BaseEntity> T update(T entity);

    <T extends BaseEntity> Long countOnCondition(T entity);

    <T extends BaseEntity> BigDecimal sumOnTondition(T entity);

    <T extends BaseEntity> List<T> search(T entity);
    
    <T extends BaseEntity> Pagination<T> searchByPage(Pagination<T> page);
}
