package com.study.toy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ToyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToyApplication.class, args);
	}

}
