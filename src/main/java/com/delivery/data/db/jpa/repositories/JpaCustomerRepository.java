package com.delivery.data.db.jpa.repositories;

import com.delivery.data.db.jpa.entities.CustomerData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCustomerRepository extends JpaRepository<CustomerData, Long> {
    boolean existsByEmail(String email);

    Optional<CustomerData> findByEmail(String email);
}
