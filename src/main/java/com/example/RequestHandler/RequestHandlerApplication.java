package com.example.RequestHandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient

public class RequestHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequestHandlerApplication.class, args);
	}
}
