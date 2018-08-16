package org.activeledger.java.sdk.activeledgerjavasdk;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("basePackages = org.activeledger.java.sdk")
public class AppConfig {

}
