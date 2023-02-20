package com.app.TeamsApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// @EnableEurekaClient is deprecated as it is automatically enabled by default.
// If you want to use some other service discovery client like zookeeper, consul, etc, use @EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class TeamsApplication {
	public static void main(String[] args) {
		SpringApplication.run(TeamsApplication.class, args);
	}
}
