package com.contoso.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ContosoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContosoApiApplication.class, args);
	}

}
