package com.github.steingrd.immensebastion;

import java.net.URI;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.github.steingrd.immensebastion")
@EnableTransactionManagement
public class AppConfiguration {

	@Bean
	public URI dbUrl() {
		String fromEnvironment = System.getenv("DATABASE_URL");
		return URI.create(fromEnvironment);
	}
	
	@Bean
	public DataSource dataSource(URI dbUrl) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://" + dbUrl.getHost() + ":" + dbUrl.getPort() + dbUrl.getPath());
		dataSource.setUsername(dbUrl.getUserInfo().split(":")[0]);
		dataSource.setPassword(dbUrl.getUserInfo().split(":")[1]);
		dataSource.setDriverClassName("org.postgresql.Driver");
		return dataSource;
	}
	
	@Bean
	public SessionFactory sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionFactoryBuilder.scanPackages("com.github.steingrd.immensebastion");
		sessionFactoryBuilder.addProperties(hibernateProperties());
		return sessionFactoryBuilder.buildSessionFactory();
	}

	@Bean
	public Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect",  "org.hibernate.dialect.PostgreSQLDialect");
        //hibernateProperties.setProperty("hibernate.show_sql", "true");
        //hibernateProperties.setProperty("hibernate.format_sql", "true");
        return hibernateProperties;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		SessionFactory sessionFactory = sessionFactory(dataSource(dbUrl()));
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}	
	
}
