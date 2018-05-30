package com.delivery.data.db.jpa.repositories;

import com.delivery.data.db.jpa.entities.CustomerData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRepository extends JpaRepository<CustomerData, Long> {
    boolean existsByEmail(String email);
}
