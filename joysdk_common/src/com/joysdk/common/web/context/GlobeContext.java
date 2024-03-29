package com.joysdk.common.web.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class GlobeContext implements ApplicationContextAware {
	private static ApplicationContext appContext; 

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		return appContext;
	}

}