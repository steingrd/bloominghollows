package com.github.steingrd.bloominghollows;

import javax.servlet.http.HttpServlet;


public class App extends HttpServlet {
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			WebApp.start();
		}
		
		if ("-client".equals(args[0])) {
			ClientApp.start();
		}
	}

	public static String propertyOrEnvVariable(String name) {
		String value = System.getProperty(name);
		if (value != null) {
			ClientApp.log.info("System property BLOOMING_HOLLOWS_TEMPERATURE_URL is [{}].", value);
			return value;
		}
	
		value = System.getenv(name);
		if (value != null) {
			ClientApp.log.info("Environment variable BLOOMING_HOLLOWS_TEMPERATURE_URL is [{}].", value);
			return value;
		}
	
		throw new RuntimeException("No environment variable or system property named " + name);
	}

}
