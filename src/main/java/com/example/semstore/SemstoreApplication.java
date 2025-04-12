package com.example.semstore;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SemstoreApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		String dbPassword = dotenv.get("DB_PASSWORD");
		String dbUrl = dotenv.get("DB_URL");
		String dbUsername = dotenv.get("DB_USERNAME");

		System.setProperty("DB_PASSWORD", dbPassword);
		System.setProperty("DB_URL", dbUrl);
		System.setProperty("DB_USERNAME", dbUsername);

		SpringApplication.run(SemstoreApplication.class, args);
	}
}