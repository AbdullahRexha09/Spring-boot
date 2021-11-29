package com.example.Authentication;

import com.example.Authentication.domain.Role;
import com.example.Authentication.domain.User;
import com.example.Authentication.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner run(UserService userService){
		return orgs ->{
			userService.saveRole(new Role(null,"ROLE_USER"));
			userService.saveRole(new Role(null,"ROLE_MANAGER"));
			userService.saveRole(new Role(null,"ROLE_ADMIN"));
			userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));


			userService.saveUser(new User(null,"Abdullah Rexha","Abdullah","1234", new ArrayList<>()));
			userService.saveUser(new User(null,"Shpetim Rexha","Shpetim","1234", new ArrayList<>()));

			userService.addRoleToUser("Abdullah","ROLE_USER");
			userService.addRoleToUser("Shpetim","ROLE_ADMIN");

		};
	}

}
