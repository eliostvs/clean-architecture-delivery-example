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
    public OutputValues execute(InputValues input) {
        if (repository.existsByEmail(input.getEmail())) {
            throw new EmailAlreadyUsedException("Email address already in use!");
        }

        Customer customer = Customer.newInstance(
                input.getName(),
                input.getEmail(),
                input.getAddress(),
                input.getPassword()
        );

        return new OutputValues(repository.persist(customer));
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
