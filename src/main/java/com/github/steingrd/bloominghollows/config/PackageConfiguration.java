package com.github.steingrd.bloominghollows.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
		"com.github.steingrd.bloominghollows.auth",
		"com.github.steingrd.bloominghollows.brews",
		"com.github.steingrd.bloominghollows.system",
		"com.github.steingrd.bloominghollows.temperatures"
})
public class PackageConfiguration {
}
