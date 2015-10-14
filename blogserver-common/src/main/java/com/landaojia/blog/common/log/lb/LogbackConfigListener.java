package com.landaojia.blog.common.log.lb;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * LogbackConfigListener(配置监听器，用于web.xml)
 * @author liuxi
 */
public class LogbackConfigListener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {
		LogbackWebConfigurer.initLogging(event.getServletContext());
	}

	public void contextDestroyed(ServletContextEvent event) {
		LogbackWebConfigurer.shutdownLogging(event.getServletContext());
	}
}
