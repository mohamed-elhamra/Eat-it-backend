package com.melhamra.eatItBackend.configuration;

import com.melhamra.eatItBackend.repositories.ImageRepository;
import com.melhamra.eatItBackend.repositories.OrderProductRepository;
import com.melhamra.eatItBackend.repositories.OrderRepository;
import com.melhamra.eatItBackend.services.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

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
                    imageEntity.setUrl("http://" + serverAddress + ":8080/api/images/" + imageEntity.getPublicId());
                    imageRepository.save(imageEntity);
                }
            });

            /*Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 0);

            Instant from = calendar.getTime().toInstant();
            Instant to = cal.getTime().toInstant();

            System.out.println(from + " --> " + to);*/

            /*Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -7);

            Instant from = cal.getTime().toInstant();
            Instant to = Instant.now();

            System.out.println(from + " --> " + to);

            orderProductRepository.getProductStatistics("7gimobHdoI0EG5v", from, to).forEach(statistic -> {
                System.out.println(statistic.getProductPublicId() + " -- " + statistic.getNumberOfCommand() + " -- " + statistic.getQuantity());
            });*/
        };
    }

}
