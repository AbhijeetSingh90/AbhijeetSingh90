package com.school.teacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TeacherApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeacherApplication.class, args);

	}

	@Bean
	@Scope("singleton")
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
