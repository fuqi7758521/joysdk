package com.joysdk.common.web.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joysdk.common.Constants;
import com.joysdk.common.acs.proxy.AcsProxy;

public class AppContextListener implements ServletContextListener{
	private static final Logger logger = LoggerFactory.getLogger(AppContextListener.class);
	
	public void contextInitialized(ServletContextEvent sce) {
		
	    final AcsProxy acsProxy=(AcsProxy)GlobeContext.getApplicationContext().getBean("acsProxy");
	    
		sce.getServletContext().setAttribute(Constants.APPLICATION_RESOURCES, acsProxy.getSimpleResources());
		
		logger.debug("AppContextListener init complated....");
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
}