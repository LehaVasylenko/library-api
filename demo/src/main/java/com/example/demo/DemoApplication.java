package com.example.demo;

import com.example.demo.services.inter.BookService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.demo.repository")
@EntityScan(basePackages = "com.example.demo.model")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DemoApplication implements CommandLineRunner {
	@Autowired
	BookService bookService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		bookService.populateBooksIfTableIsEmpty();
	}

}
