package com.graduation.letter;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.TimeZone;

@SpringBootApplication
@EnableCaching
public class LetterApplication {

	@PostConstruct
	public void init() {
		// Force the JVM to use a recognized timezone before connecting to the DB
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
	}

	public static void main(String[] args) {

		SpringApplication.run(LetterApplication.class, args);
	}

}
