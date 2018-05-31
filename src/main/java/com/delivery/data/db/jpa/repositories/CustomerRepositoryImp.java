package com.delivery.data.db.jpa.repositories;

import com.delivery.core.usecases.customer.CreateCustomerInput;
import com.delivery.core.usecases.customer.Customer;
import com.delivery.core.usecases.customer.CustomerRepository;
import com.delivery.data.db.jpa.entities.CustomerData;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepositoryImp implements CustomerRepository {
    private JpaCustomerRepository repository;

    public CustomerRepositoryImp(JpaCustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer save(CreateCustomerInput customerInput) {
        final CustomerData customerData = CustomerData.from(customerInput);
        return repository.save(customerData).fromThis();
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<CustomerData> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<CustomerData> findById(Long id) {
        return repository.findById(id);
    }
}
