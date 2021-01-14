package com.melhamra.eatItBackend.configuration;

import com.melhamra.eatItBackend.services.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Resource
    ImageService imageService;

    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean
    public CommandLineRunner start(){
        return args -> imageService.init();
    }

}
