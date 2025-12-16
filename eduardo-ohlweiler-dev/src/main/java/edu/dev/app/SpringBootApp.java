package edu.dev.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "edu.dev.*" })
@EntityScan(basePackages = { "edu.dev.entity", "edu.dev.entity2" })
@ComponentScan(basePackages = { "edu.dev.*" })
@EnableJpaRepositories(basePackages = { "edu.dev.repository" })
public class SpringBootApp {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp.class, args);
	}
	
}
