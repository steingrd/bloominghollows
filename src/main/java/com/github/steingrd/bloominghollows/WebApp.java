package com.github.steingrd.bloominghollows;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.github.steingrd.bloominghollows.config.AppConfiguration;

public class WebApp {

	public static void main(String[] args) throws Exception {
		start();
	}
	
	public static void start() throws Exception {
		Server server = new Server(port());
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
		context.setInitParameter("contextConfigLocation", AppConfiguration.class.getName());
		context.addServlet(defaultServlet(), "/*");
		context.addServlet(dispatcherServlet(), "/service/*");
		context.addFilter(OpenSessionInViewFilter.class, "/service/*", null);
		context.addEventListener(new ContextLoaderListener());
		
		server.setHandler(context);
		
		// let's start this thing now...
		server.start();
		server.join();
	}

	private static ServletHolder defaultServlet() {
		String resourceBase = App.class.getClassLoader().getResource("web/").toExternalForm();
		
		ServletHolder holder = new ServletHolder(DefaultServlet.class);
		holder.setInitParameter("resourceBase", resourceBase);
		holder.setInitParameter("dirAllowed", "true");
		holder.setInitParameter("pathInfoOnly", "true");
		
		return holder;
	}

	private static ServletHolder dispatcherServlet() {
		ServletHolder holder = new ServletHolder(new DispatcherServlet());
		holder.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
		holder.setInitParameter("contextConfigLocation", AppConfiguration.class.getName());
		return holder;
	}

	private static Integer port() {
		String port = System.getenv("PORT");
		if (port == null) {
			port = "9090";
		}
		return Integer.valueOf(port);
	}
}
