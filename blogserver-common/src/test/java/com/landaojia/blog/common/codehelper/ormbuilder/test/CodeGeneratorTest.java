package com.landaojia.blog.common.codehelper.ormbuilder.test;


import com.landaojia.blog.common.codehelper.ormbuilder.CodeGenerator;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

/**
 * @author codingwoo <long1795@gmail.com>
 */
public class CodeGeneratorTest {
    BasicDataSource ds;

    @Before
    public void before() {
        ds = new BasicDataSource();

        init(ds, "blog_db");
    }


    void init(BasicDataSource ds, String schema) {
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("landaojia");
        ds.setPassword("LdjMySQL@#$20150826");
        ds.setUrl("jdbc:mysql://120.26.73.158:16033/" + schema + "?useUnicode=true&characterEncoding=UTF-8");

    }


    @Test
    public void test() throws Exception {
        new CodeGenerator(ds, "blog_db", "com.landaojia.blog.post.entity").generateCode(false, "E:/Git_Local/blog_api/blogserver-service/src/main/java/", "b_", "b_post_attachment");
    }


}
