package com.delivery.core.usecases.customer;

import com.delivery.data.db.jpa.entities.CustomerData;

import java.util.Optional;

public interface CustomerRepository {
    Customer save(CreateCustomerInput input);

    boolean existsByEmail(String email);

    Optional<CustomerData> findByEmail(String email);

    Optional<CustomerData> findById(Long id);
}
