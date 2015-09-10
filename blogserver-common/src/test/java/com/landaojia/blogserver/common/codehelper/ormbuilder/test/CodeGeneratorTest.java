package com.landaojia.blogserver.common.codehelper.ormbuilder.test;

import com.landaojia.blogserver.common.codehelper.ormbuilder.CodeGenerator;
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

		init(ds, "test");
	}

	void init(BasicDataSource ds, String schema) {
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername("root");
		ds.setPassword("111111");
		ds.setUrl("jdbc:mysql://127.0.0.1:3306/" + schema + "?useUnicode=true&characterEncoding=UTF-8");
	}

	@Test
	public void test() throws Exception {

		new CodeGenerator(ds, "test", "com.landaojia.blogserver.dao").generateCode(false, "../blogserver-dao/src/main/java/", "t_", "t_user");

	}
}
