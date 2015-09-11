package com.landaojia.blog.blog.common.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.landaojia.blog.blog.common.exception.CommonException;

@Repository
public class CommonDaoImpl extends SqlSessionDaoSupport implements CommonDao {

    @SuppressWarnings({ "serial" })
    private static final Map<String, String> statementMap = new HashMap<String, String>() {

        {
            put("get", ".getByPrimaryKey");
            put("remove", ".remove");
            put("create", ".insert");
            put("update", ".update");
            put("countAll", ".countAll");
            put("searchByMap", ".searchByMap");
            put("search", ".search");
        }
    };

    public <C extends BaseEntity, ID extends Serializable> C findById(Class<C> clazz, ID id) {
        if (id == null || clazz == null) throw new CommonException("操作失败");
        return getSqlSession().selectOne(clazz.getSimpleName() + statementMap.get("get"), id);
    }

    @Transactional
    public <C extends BaseEntity, ID extends Serializable> void remove(Class<C> clazz, ID id) {
        if (id == null || clazz == null) throw new CommonException("操作失败");
        getSqlSession().delete(clazz.getSimpleName() + statementMap.get("remove"), id);
    }

    @Transactional
    public <T extends BaseEntity> T create(T entity) {
        if (entity == null) throw new CommonException("操作失败");
        getSqlSession().insert(entity.getClass().getSimpleName() + statementMap.get("create"), entity);
        // TODO 返回值含义
        return entity;
    }

    @Transactional
    public <T extends BaseEntity> T update(T entity) {
        if (entity == null) throw new CommonException("操作失败");
        getSqlSession().update(entity.getClass().getSimpleName() + statementMap.get("update"), entity);
        // TODO 返回值含义
        return entity;
    }

    public <C extends BaseEntity> Long countAll(Class<C> clazz) {
        if (clazz == null) return 0L;
        return getSqlSession().selectOne(clazz.getSimpleName() + statementMap.get("countAll"));
    }

    public <C extends BaseEntity> List<C> searchByMap(Class<C> clazz, Map<String, Object> paramMap) {
        if (clazz == null || paramMap == null) return new ArrayList<C>();
        return getSqlSession().selectList(clazz.getSimpleName() + statementMap.get("searchByMap"), paramMap);
    }

    @SuppressWarnings("unchecked")
    public <C extends BaseEntity> List<C> search(C entity) {
        if(entity == null) return new ArrayList<C>();
        try {
            return searchByMap(entity.getClass(), BeanUtils.describe(entity));
        } catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
            return new ArrayList<C>();
        }
    }
}
