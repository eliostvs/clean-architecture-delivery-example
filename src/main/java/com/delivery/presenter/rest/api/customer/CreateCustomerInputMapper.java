package com.delivery.presenter.rest.api.customer;

import com.delivery.core.usecases.customer.CreateCustomerInput;
import com.delivery.presenter.rest.api.entities.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerInputMapper {
    private PasswordEncoder passwordEncoder;

    public CreateCustomerInputMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public CreateCustomerInput map(SignUpRequest signUpRequest) {
        return new CreateCustomerInput(
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                signUpRequest.getAddress(),
                passwordEncoder.encode(signUpRequest.getPassword()));
    }
}
