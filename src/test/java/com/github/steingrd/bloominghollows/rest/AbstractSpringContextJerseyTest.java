package com.github.steingrd.bloominghollows.rest;

import org.springframework.web.context.ContextLoaderListener;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerException;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;

public abstract class AbstractSpringContextJerseyTest extends JerseyTest {

	public AbstractSpringContextJerseyTest() throws TestContainerException {
		super();
	}

	public AbstractSpringContextJerseyTest(TestContainerFactory testContainerFactory) {
		super(testContainerFactory);
	}

	public AbstractSpringContextJerseyTest(AppDescriptor ad) throws TestContainerException {
		super(ad);
	}

	public AbstractSpringContextJerseyTest(String... packages) throws TestContainerException {
		super(packages);
	}

	@Override
	protected AppDescriptor configure() {
		System.setProperty("DATABASE_URL", "postgres://steingrd:pukkverk@127.0.0.1:5432/immensebastion");
		return new WebAppDescriptor
			.Builder("com.github.steingrd.immensebastion.rest")
			.contextPath("/rest")
			.initParam("com.sun.jersey.config.property.packages", "org.codehaus.jackson.jaxrs")
			.contextParam("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext")
			.contextParam("contextConfigLocation", "com.github.steingrd.immensebastion.AppConfiguration")
			.servletClass(SpringServlet.class)
			.contextListenerClass(ContextLoaderListener.class)
			.build();
	}

}