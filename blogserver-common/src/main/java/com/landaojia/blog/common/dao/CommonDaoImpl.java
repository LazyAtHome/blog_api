package com.landaojia.blog.common.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.landaojia.blog.common.exception.CommonException;

public class CommonDaoImpl extends SqlSessionDaoSupport implements CommonDao {

    public static final String FAIL_OPERATION = "操作失败";

    @SuppressWarnings({ "serial" })
    private static final Map<String, String> statementMap = new HashMap<String, String>() {

        {
            put("select", ".select");
            put("remove", ".deleteById");
            put("removeOnCondition", ".delete");
            put("create", ".insert");
            put("createWithId", ".insertWithId");
            put("createBatch", ".batchInsert");
            put("createBatchWithId", ".batchInsertWithId");
            put("update", ".update");
            put("countOnCondition", ".count");
            put("sumOnCondition", ".sum");
            put("search", ".query");
        }
    };

    @Override
    public <T extends BaseEntity, ID extends Serializable> T findById(Class<T> clazz, ID id) {
        if (id == null || clazz == null) throw new CommonException(FAIL_OPERATION);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("lock", false);
        return getSqlSession().selectOne(clazz.getName() + statementMap.get("select"), paramMap);
    }

    @Override
    @Transactional
    public <T extends BaseEntity, ID extends Serializable> void removeById(Class<T> clazz, ID id) {
        if (id == null || clazz == null) throw new CommonException(FAIL_OPERATION);
        getSqlSession().delete(clazz.getName() + statementMap.get("remove"), id);
    }

    @Override
    @Transactional
    public <T extends BaseEntity> void removeOnCondition(T entity) {
        if (entity == null) throw new CommonException(FAIL_OPERATION);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cond", entity);
        getSqlSession().update(entity.getClass().getName() + statementMap.get("removeOnCondition"), paramMap);
    }

    @Override
    @Transactional
    public <T extends BaseEntity> T insert(T entity) {
        if (entity == null) throw new CommonException(FAIL_OPERATION);
        getSqlSession().insert(entity.getClass().getName() + statementMap.get("create"), entity);
        // TODO 返回值含义
        return entity;
    }

    @Override
    @Transactional
    public <T extends BaseEntity> T insertWithId(T entity) {
        if (entity == null) throw new CommonException(FAIL_OPERATION);
        getSqlSession().insert(entity.getClass().getName() + statementMap.get("createWithId"), entity);
        // TODO 返回值含义
        return entity;
    }

    @Override
    @Transactional
    public <T extends BaseEntity> List<T> batchInsert(List<T> entities) {
        if (entities == null) throw new CommonException(FAIL_OPERATION);
        try {
            getSqlSession().insert(entities.getClass().getMethod("get", null).getReturnType().getClass().getName() + statementMap.get("createBatch"), entities);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new CommonException(FAIL_OPERATION);
        }
        return entities;
    }

    @Override
    @Transactional
    public <T extends BaseEntity> List<T> batchInsertWithId(List<T> entities) {
        if (entities == null) throw new CommonException(FAIL_OPERATION);
        try {
            getSqlSession().insert(entities.getClass().getMethod("get", null).getReturnType().getClass().getName() + statementMap.get("createBatchWithId"), entities);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new CommonException(FAIL_OPERATION);
        }
        return entities;
    }

    @Override
    @Transactional
    public <T extends BaseEntity> T update(T entity) {
        if (entity == null) throw new CommonException(FAIL_OPERATION);
        getSqlSession().update(entity.getClass().getName() + statementMap.get("update"), entity);
        // TODO 返回值含义
        return entity;
    }

    @Override
    public <T extends BaseEntity> Long countOnCondition(T entity) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cond", entity);
        return getSqlSession().selectOne(entity.getClass().getName() + statementMap.get("countOnTondition"), paramMap);
    }

    @Override
    public <T extends BaseEntity> BigDecimal sumOnTondition(T entity) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cond", entity);
        return getSqlSession().selectOne(entity.getClass().getName() + statementMap.get("sumOnTondition"), paramMap);
    }

    @Override
    public <T extends BaseEntity> List<T> search(T entity) {
        if (entity == null) return new ArrayList<T>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cond", entity);
        return getSqlSession().selectList(entity.getClass().getName() + statementMap.get("search"), paramMap);
    }

    @Override
    public <T extends BaseEntity> Pagination<T> searchByPage(Pagination<T> page) {
        if (page == null) return new  Pagination<T>();
        return  page.setResults(getSqlSession().selectList(page.getCondClass().getName() + statementMap.get("search"), page.getCondMap(), page));
    }
}
