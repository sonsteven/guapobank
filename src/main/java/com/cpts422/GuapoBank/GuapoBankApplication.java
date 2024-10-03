package com.cpts422.GuapoBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GuapoBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuapoBankApplication.class, args);
	}

}
