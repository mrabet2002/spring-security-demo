package com.example.springsecuritydemo;

import com.example.springsecuritydemo.config.JwtConfig;
import com.example.springsecuritydemo.dto.RegisterRequest;
import com.example.springsecuritydemo.services.interfaces.IAuthenticationService;
import com.example.springsecuritydemo.services.interfaces.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class SpringSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner start(
			IAuthenticationService authenticationService
	){
		return args -> {
			RegisterRequest request = new RegisterRequest(
					"ahmed", "ahmed@gmail.com", "12345678"
			);
			authenticationService.register(request);
		};
	}

}
