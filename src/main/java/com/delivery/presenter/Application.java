package com.delivery.presenter;

import com.delivery.data.db.jpa.cousine.CousineData;
import com.delivery.data.db.jpa.cousine.JpaCousineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = {"com.delivery.presenter", "com.delivery.data.db.jpa"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner run(JpaCousineRepository jpaCousineRepository) {
        return (args) -> Arrays.stream(new String[]{"Chinese", "Pizza", "Shushi"})
                .forEach(name -> jpaCousineRepository.save(new CousineData(null, name)));
    }
}
