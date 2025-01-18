package com.findar.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class BookstoreManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreManagementApplication.class, args);
	}

}
