package business.service;

import business.entity.Article;

public interface ArticleService {
    
    /**
     * create an article
     * @param subject
     * @param content
     * @param creatorId
     * @return
     */
    public Article submit(String subject, String content, Long creatorId);
    
    /**
     * delete an article by primary key
     * @param articleId
     * @return
     */
    public void delete(Long articleId);
    
}
