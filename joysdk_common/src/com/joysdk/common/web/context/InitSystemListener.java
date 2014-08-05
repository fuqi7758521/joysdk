package com.joysdk.common.web.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitSystemListener implements ServletContextListener {

    final static Logger loger=LoggerFactory.getLogger(InitSystemListener.class);

    private static final String CONFIG_LOCATION_PARAM="initConfigLocation";

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext=event.getServletContext();

        loger.info("initialize system properties");

        String configFile=servletContext.getInitParameter(CONFIG_LOCATION_PARAM);

        if(StringUtils.isNotBlank(configFile)) {
            SystemProperties.loadPropertyFileByPatterns(configFile);
        }
        // 加载受保护资源放到servletContext中，以便在secretFilter中使用
    }

}
