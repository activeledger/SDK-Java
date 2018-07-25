package org.activeledger.java.sdk.activeledgerjavasdk;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan({"basePackages = org.activeledger.java.sdk.onboard","org.activeledger.java.sdk.key.management"})
@ComponentScan("basePackages = org.activeledger.java.sdk")
public class AppConfig {

}
