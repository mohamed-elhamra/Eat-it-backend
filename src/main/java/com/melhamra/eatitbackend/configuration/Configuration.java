package com.melhamra.eatitbackend.configuration;

import com.melhamra.eatitbackend.repositories.ImageRepository;
import com.melhamra.eatitbackend.repositories.OrderProductRepository;
import com.melhamra.eatitbackend.services.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

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
    public CommandLineRunner start(ImageRepository imageRepository, Environment environment, OrderProductRepository orderProductRepository) {
        return args -> {
            imageService.init();
            String serverAddress = environment.getProperty("server.address");
            imageRepository.findAll().forEach(imageEntity -> {
                assert serverAddress != null;
                if (!serverAddress.equals(imageEntity.getUrl().substring(7, 20))) {
                    imageEntity.setUrl("http://" + serverAddress + ":8080/api/v1/images/" + imageEntity.getPublicId());
                    imageRepository.save(imageEntity);
                }
            });
        };
    }

}
