package com.delivery.core.usecases.customer;

import com.delivery.core.domain.Customer;
import com.delivery.data.db.jpa.entities.CustomerData;

import java.util.Optional;

public interface CustomerRepository {
    Customer persist(Customer customer);

    boolean existsByEmail(String email);

    Optional<CustomerData> findByEmail(String email);

    Optional<CustomerData> findById(Long id);
}
