package business.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.entity.Article;
import business.service.ArticleService;
import core.base.CommonRepository;
import core.base.exception.AppException;
import core.base.utils.DateUtil;
import core.base.utils.StringUtil;

@Service
public class ArticleServiceImpl implements ArticleService {
    
    @Resource
    CommonRepository commonRepository;

    @Override
    public Article submit(String subject, String content, Long creatorId) {
        if(StringUtil.isNullOrEmpty(subject, content) || creatorId == null) throw new AppException("操作失败");
        Article art = new Article(creatorId, subject, content);
        Date currDate = DateUtil.getCurrentDate();
        art.setCreateTime(currDate);
        art.setUpdateTime(currDate);
        return commonRepository.create(art);
    }

    @Override
    public void delete(Long articleId) {
        Article article = commonRepository.get(Article.class, articleId);
        if(article != null){
            article.setIsDeleted(Article.ISDELETED_YES);
            article.setUpdateTime(DateUtil.getCurrentDate());
        }
        commonRepository.update(article);
    }
}
