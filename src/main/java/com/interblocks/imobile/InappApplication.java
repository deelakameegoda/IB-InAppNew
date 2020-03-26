package com.interblocks.imobile;

import com.interblocks.iwallet.config.core.CoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/*
@ComponentScan(basePackages = {
		"com.interblocks.iwallet.config.core",
		"com.interblocks.iwallet.config.database",
		"com.interblocks.iwallet.config.hazelcast",
		"com.interblocks.webtools"
})
@EnableJpaRepositories(basePackages = {
		"com.interblocks.webtools"
})*/
@SpringBootApplication
@EnableEurekaClient
//@Import(CoreConfig.class)
public class InappApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InappApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(InappApplication.class, args);
	}

}
