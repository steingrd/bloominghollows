package com.github.steingrd.bloominghollows;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App extends HttpServlet {
	
	private static final Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			WebApp.start();
		}
		
		if ("-client".equals(args[0])) {
			ClientApp.start();
		}
	}

	public static String propertyOrEnvVariable(String name) {
		String value = propertyOrEnvVariableWithDefault(name, null);
		if (value == null) {
			throw new RuntimeException("No environment variable or system property named " + name);
		}
		return value;
	}
	
	public static String propertyOrEnvVariableWithDefault(String name, String defaultValue) {
		String value = System.getProperty(name);
		if (value != null) {
			log.info("System property [{}] is [{}].", name, value);
			return value;
		}
	
		value = System.getenv(name);
		if (value != null) {
			log.info("Environment variable [{}] is [{}].", name, value);
			return value;
		}
		
		log.info("No environment variable or system property with name [{}], using default value [{}].", name, defaultValue);
	
		return defaultValue;
	}

}
