package com.delivery.presenter.usecases.security;

import com.delivery.core.usecases.customer.CreateCustomerUseCase;
import com.delivery.presenter.rest.api.entities.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerInputMapper {
    private PasswordEncoder passwordEncoder;

    public CreateCustomerInputMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public CreateCustomerUseCase.InputValues map(SignUpRequest signUpRequest) {
        return new CreateCustomerUseCase.InputValues(
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                signUpRequest.getAddress(),
                passwordEncoder.encode(signUpRequest.getPassword()));
    }
}
