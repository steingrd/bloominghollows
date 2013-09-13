package com.github.steingrd.immensebastion;

import java.net.URI;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

	@Bean
	public URI dbUrl(@Value("${DATABASE_URL}") String fromEnvironment) {
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
	
}
