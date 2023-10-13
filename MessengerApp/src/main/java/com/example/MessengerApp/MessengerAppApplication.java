package com.example.MessengerApp;

import com.example.MessengerApp.controllers.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.example.MessengerApp.service"})
@EntityScan("com.example.MessengerApp.model")
@EnableJpaRepositories("com.example.MessengerApp.repository")
@ComponentScan(basePackageClasses = UserController.class)
public class MessengerAppApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MessengerAppApplication.class, args);
	}

}
