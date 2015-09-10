package business.entity;

import java.util.Date;

import core.base.BaseEntity;

/**
 * @author Jason
 *
 * 2015年9月9日
 */
public class Article extends BaseEntity {
    
    //constants start
    public static final Integer ISDELETED_NO = 0;
    public static final Integer ISDELETED_YES = 1;
    //constants end

    private static final long serialVersionUID = 1L;
    
    private Long creatorId;//创建者ID
    
    private String subject;//主题
    
    private String content;//内容
    
    private Date createTime;//创建时间
    
    private Date updateTime;//最近修改时间
    
    private Long viewCount;//浏览量
    
    private Integer isDeleted; //是否被删除

    
    public Long getCreatorId() {
        return creatorId;
    }

    
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    
    public String getSubject() {
        return subject;
    }

    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    
    public String getContent() {
        return content;
    }

    
    public void setContent(String content) {
        this.content = content;
    }

    
    public Date getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    public Date getUpdateTime() {
        return updateTime;
    }

    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    
    public Long getViewCount() {
        return viewCount;
    }

    
    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }


    
    public Integer getIsDeleted() {
        return isDeleted;
    }


    
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }


    public Article(Long creatorId, String subject, String content) {
        this.creatorId = creatorId;
        this.subject = subject;
        this.content = content;
        this.isDeleted = Article.ISDELETED_NO;
        this.viewCount = 0L;
    }


    public Article() {
    }
    
    
    
}
