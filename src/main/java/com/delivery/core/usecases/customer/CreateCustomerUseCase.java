package com.delivery.core.usecases.customer;

import com.delivery.core.domain.EmailAlreadyUsedException;
import com.delivery.core.usecases.UseCase;

public class CreateCustomerUseCase implements UseCase<CreateCustomerInput, Customer> {
    private CustomerRepository repository;

    public CreateCustomerUseCase(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer execute(CreateCustomerInput customerInput) {
        if (repository.existsByEmail(customerInput.getEmail())) {
            throw new EmailAlreadyUsedException("Email address already in use!");
        }

        return repository.save(customerInput);
    }
}
