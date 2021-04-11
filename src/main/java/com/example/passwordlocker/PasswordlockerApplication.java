package com.example.passwordlocker;

import com.example.passwordlocker.repositories.AccountRepository;
import com.example.passwordlocker.repositories.LogRepository;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {
		UserRepository.class, AccountRepository.class, LogRepository.class
})
public class PasswordlockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasswordlockerApplication.class, args);
	}

}
