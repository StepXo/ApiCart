package com.emazon.ApiCart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCartApplication.class, args);
	}

}
