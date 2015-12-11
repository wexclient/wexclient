package com.livngroup.gds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ImportResource(locations = "classpath:wex-client-rest-app-context.xml")
@ComponentScan(basePackages = "com.livngroup.gds")
@EnableScheduling
@PropertySource("classpath:application-properties.xml")
public class LivnDemoApplication {
	
	public static ApplicationContext APPLICATION_CONTEXT;
	
	final private static Logger logger = LoggerFactory.getLogger(LivnDemoApplication.class);

    public static void main(String[] args) {
    	System.setProperty("banner.location", "classpath:wex-client-banner.txt");
    	APPLICATION_CONTEXT = SpringApplication.run(LivnDemoApplication.class, args);
        logger.info("application started [" + System.getProperty("spring.profiles.active") + "]...");
    }
}
