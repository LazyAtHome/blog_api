package business.repository.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import business.repository.ArticleRepository;

/**
 * for extending sql query
 * @author Jason
 *
 * 2015年9月9日
 */
@Repository
public class ArticleRepositoryImpl extends SqlSessionDaoSupport implements ArticleRepository {
    
}
