package com.basic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringFactory {

	private static ApplicationContext springContext;

	public static void setContext(ApplicationContext context) {
		springContext = context;
	}

	private static ApplicationContext getContext() {
		if (springContext == null) {
			springContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
		return springContext;
	}

	public static Object getBean(String id) {
		return getContext().getBean(id);
	}
	
}
