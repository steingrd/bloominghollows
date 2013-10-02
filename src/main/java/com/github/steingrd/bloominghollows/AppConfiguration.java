package com.github.steingrd.bloominghollows;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.github.steingrd.immensebastion")
@EnableTransactionManagement
@Import({DbConfiguration.class})
public class AppConfiguration {
}
