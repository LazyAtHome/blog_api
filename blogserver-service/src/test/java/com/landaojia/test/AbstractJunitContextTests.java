package com.landaojia.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/blogserver-dao.xml","classpath:META-INF/spring/blogserver-web-config.xml", "classpath:META-INF/spring/blogserver-context.xml"})
public class AbstractJunitContextTests extends AbstractTransactionalJUnit4SpringContextTests{

}
