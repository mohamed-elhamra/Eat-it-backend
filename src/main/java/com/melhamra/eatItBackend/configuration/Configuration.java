package com.melhamra.eatItBackend.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean
    CommandLineRunner start(){
        return args -> {
            String name= "mohamed";
            Arrays.stream(name.split(",")).forEach(System.out::println);
            System.out.println(SpringApplicationContext.getBean("userServiceImpl").toString());
        };
    }

}
