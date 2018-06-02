package com.delivery.core.usecases.customer;

import com.delivery.core.domain.Customer;
import com.delivery.core.domain.EmailAlreadyUsedException;
import com.delivery.core.usecases.UseCase;
import lombok.Value;

public class CreateCustomerUseCase extends UseCase<CreateCustomerUseCase.InputValues, CreateCustomerUseCase.OutputValues> {
    private CustomerRepository repository;

    public CreateCustomerUseCase(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues customerInput) {
        if (repository.existsByEmail(customerInput.getEmail())) {
            throw new EmailAlreadyUsedException("Email address already in use!");
        }

        // TODO: convert to Customer first
        return new OutputValues(repository.persist(customerInput));
    }

    @Value
    public static class InputValues implements UseCase.InputValues{
        private final String name;
        private final String email;
        private final String address;
        private final String password;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final Customer customer;
    }
}
