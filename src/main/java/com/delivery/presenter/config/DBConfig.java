package com.delivery.presenter.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.delivery.data.db.jpa.entities"})
@EnableJpaRepositories(basePackages = {"com.delivery.data.db.jpa.repositories"})
public class DBConfig {
}
