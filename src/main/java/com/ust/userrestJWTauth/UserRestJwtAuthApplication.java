package com.ust.userrestJWTauth;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ust.userrestJWTauth.model.User;
import com.ust.userrestJWTauth.repository.UserRepository;

@SpringBootApplication
public class UserRestJwtAuthApplication {
	
	@Autowired
	UserRepository userRepo;
	
	@PostConstruct
	public void storeUsersToDatabase()
	{
		List<User> users = Stream.of(
				new User(101, "zaid", "zaid123", "zaid@gmail"),
				new User(122, "user1", "user123", "user@gmail"),
				new User(133, "user2", "user123", "user123@gmail")
				).collect(Collectors.toList());
		
		userRepo.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(UserRestJwtAuthApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// TODO Auto-generated method stub
				registry.addMapping("/*").allowedHeaders("*").allowedOrigins("*").allowedMethods("*").allowCredentials(false);
			}
		};
	}

}
