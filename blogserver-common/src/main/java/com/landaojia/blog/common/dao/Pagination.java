package com.landaojia.blog.common.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.landaojia.blog.common.exception.CommonException;
import com.landaojia.blog.common.exception.CommonExceptionCode;

/**
 * this is a pagination object used for storing result set; 
 * @author Jason
 *
 * 2015年9月16日
 */
public class Pagination<T extends BaseEntity> extends PageBounds {
    
    private static final long serialVersionUID = 1L;
    
    private static final int DEFAULT_LIMIT = 20;
    
    private List<T> results = new ArrayList<T>();
    
    private T cond;
    
    private Map<String, Object> condMap;
    
    private Class<T> entityClass;
    
    public List<T> getResults() {
        return results;
    }

    
    public Pagination<T> setResults(List<T> results) {
        this.results = results;
        return this;
    }
    
    public Pagination(int page, int limit, Order... orders) {
        super(page, limit, orders);
    }
    
    public Pagination(T cond, int page, int limit, Order... orders) {
        super(page, limit, orders);
        this.cond = cond;
    }
    
    public Pagination(T cond) {
        super(PageBounds.NO_PAGE, DEFAULT_LIMIT);
        this.cond = cond;
    }
    
    public Pagination(Class<T> entityClass, Map<String, Object> condMap, int page, int limit, Order... orders) {
        super(page, limit, orders);
        this.condMap = condMap;
        this.entityClass = entityClass;
    }
    public Pagination(Class<T> entityClass, Map<String, Object> condMap) {
        super(PageBounds.NO_PAGE, DEFAULT_LIMIT);
        this.condMap = condMap;
        this.entityClass = entityClass;
    }
    
    public Pagination() {
        super(PageBounds.NO_PAGE, DEFAULT_LIMIT);
    }


    @Override
    public String toString() {
        return super.toString()+ "    results:"+results.size();
    }
    
    /**
     * map is first
     * @return
     */
    public Map<String, Object> getCondMap(){
        if(condMap != null) return condMap;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cond", cond);
        return paramMap;
    }
    
    public Class<?> getCondClass(){
        if(this.entityClass != null) return entityClass;
        if(this.cond != null)
            return cond.getClass();
        throw new CommonException(CommonExceptionCode.E999999);
    }
}
