package com.melhamra.eatItBackend;

import com.melhamra.eatItBackend.configuration.SpringApplicationContext;
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

}


