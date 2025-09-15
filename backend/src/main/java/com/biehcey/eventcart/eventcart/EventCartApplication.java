package com.biehcey.eventcart.eventcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EventCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventCartApplication.class, args);
	}

}
