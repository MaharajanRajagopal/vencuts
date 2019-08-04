package com.vencuts.boot.rapo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.vencuts.boot" )
public class RapoApplication  {
	  
	public static void main(String[] args) {
		SpringApplication.run(RapoApplication.class, args);
	}
	
}
