package com.spc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
@EnableWebSocket
@SpringBootApplication
@EnableEurekaClient //注册到注册中心去
public class MemberServer {

	public static void main(String[] args) {
		SpringApplication.run(MemberServer.class, args);
	}
}

