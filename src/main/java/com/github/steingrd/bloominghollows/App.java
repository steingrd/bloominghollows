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

}
