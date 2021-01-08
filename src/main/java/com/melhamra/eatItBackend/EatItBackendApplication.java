package com.melhamra.eatItBackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class EatItBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatItBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner start(){
		return args -> {
			String name= "mohamed";
			Arrays.stream(name.split(",")).forEach(System.out::println);
		};
	}

}


