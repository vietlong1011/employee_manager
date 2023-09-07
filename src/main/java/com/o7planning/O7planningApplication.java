package com.o7planning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
//@EnableJpaRepositories("com.o7planning.repository")
public class O7planningApplication {

	public static void main(String[] args) {
		SpringApplication.run(O7planningApplication.class, args);
		System.out.println("Hello SpringBoot");
	}

}
