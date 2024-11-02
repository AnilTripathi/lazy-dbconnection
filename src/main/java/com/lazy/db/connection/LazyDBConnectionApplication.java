package com.lazy.db.connection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class LazyDBConnectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(LazyDBConnectionApplication.class, args);
	}

}
