package com.test.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CrowdMainType {
	
	public static void main(String[] args) {
		SpringApplication.run(CrowdMainType.class, args);
	}

}