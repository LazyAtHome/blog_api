package business.sys.repository.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import business.sys.repository.UserRepository;

/**
 * for extending sql query
 * @author Jason
 *
 * 2015年9月9日
 */
@Repository
public class UserRepositoryImpl extends SqlSessionDaoSupport implements UserRepository {
    
}
