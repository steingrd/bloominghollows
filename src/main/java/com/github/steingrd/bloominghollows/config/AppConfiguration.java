package com.github.steingrd.bloominghollows.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DbConfiguration.class, WebConfiguration.class, PackageConfiguration.class})
public class AppConfiguration {
}
