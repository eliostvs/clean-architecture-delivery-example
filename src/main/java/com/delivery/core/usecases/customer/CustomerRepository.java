package com.delivery.core.usecases.customer;

public interface CustomerRepository {
    Customer save(CreateCustomerInput input);

    boolean existsByEmail(String email);
}
