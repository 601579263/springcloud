package com.spc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * 访问地址http://localhost:端口号/swagger-ui.html#/
 *
 */
@SpringBootApplication
public class SwaggerServer {

	public static void main(String[] args) {
		SpringApplication.run(SwaggerServer.class, args);
	}

}
