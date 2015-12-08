package com.livngroup.gds;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ImportResource(locations = "classpath:wex-client-rest-app-context.xml")
@ComponentScan(basePackages = "com.livngroup.gds")
@EnableScheduling
public class LivnDemoApplication {
	
	final private static Logger logger = Logger.getLogger(LivnDemoApplication.class);

    public static void main(String[] args) {
    	System.setProperty("banner.location", "classpath:wex-client-banner.txt");
        SpringApplication.run(LivnDemoApplication.class, args);
        logger.debug("application started ...");
    }
}
