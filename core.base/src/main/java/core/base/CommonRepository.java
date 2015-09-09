package core.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * It is used to integrate all of the basic CRUD operations
 * @author Jason
 *
 * 2015年9月8日
 */
public interface CommonRepository {
    
    /**
     * Get any type of entity by class and primary key of this entity 
     * @param clazz
     * @param id
     * @return
     */
    public <C extends BaseEntity, ID extends Serializable> C get(Class<C> clazz, ID id);
    
    /**
     * Remove any type of entity by class and primary key of this entity
     * @param clazz
     * @param id
     */
    public <C extends BaseEntity, ID extends Serializable> void remove(Class<C> clazz, ID id);
    
    /**
     * Create any type of entity
     * @param entity
     * @return
     */
    public <T extends BaseEntity> T create(T entity);
    
    /**
     * Update any type of entity
     * @param entity
     * @return
     */
    public <T extends BaseEntity> T update(T entity);
    
    /**
     * Count all of the entity
     * @return
     */
    public <C extends BaseEntity> Long countAll(Class<C> clazz);
    
    /**
     * Search any type of entity by Map;
     * @param clazz
     * @param paramMap
     * @return
     */
    public <C extends BaseEntity> List<C> searchByMap(Class<C> clazz, Map<String, Object> paramMap); 
    
    /**
     * Search any type of entity by object of entity;
     * @param clazz
     * @param paramMap
     * @return
     */
    public <C extends BaseEntity> List<C> search(C entity); 
}
