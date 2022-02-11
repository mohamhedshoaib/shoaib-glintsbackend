package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.demo.service.RestaurantsService;
import com.example.demo.service.UserService;

@SpringBootApplication
@EnableJpaRepositories
public class DemoApplication implements CommandLineRunner {

	@Autowired
	UserService userService;
	
	@Autowired
	RestaurantsService resService;
	
	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("Save Users from GIT JSON file to H2 ");
		userService.saveUsersToH2();
		
		System.out.println("Save Restaurnats from GIT JSON file to H2 ");
		resService.saveRestaurnatsToH2();

	}

}
