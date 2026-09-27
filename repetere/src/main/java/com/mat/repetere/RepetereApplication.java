package com.mat.repetere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RepetereApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepetereApplication.class, args);
	}

}
