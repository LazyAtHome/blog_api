package core.base;

import java.io.Serializable;

/**
 * base entity for every business entity;
 * 
 * @author Jason 2015年9月8日
 */
public abstract class BaseEntity implements Entity<Long>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Entity)) {
            return false;
        }
        Entity<?> other = (Entity<?>) obj;
        if (id == null) {
            return false;
        }
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : id.hashCode();
    }

}
