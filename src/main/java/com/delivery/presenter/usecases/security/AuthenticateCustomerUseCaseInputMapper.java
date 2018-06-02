package com.delivery.presenter.usecases.security;

import com.delivery.presenter.rest.api.entities.SignInRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public final class AuthenticateCustomerUseCaseInputMapper {
    public static AuthenticateCustomerUseCase.InputValues map(SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),
                signInRequest.getPassword());

        return new AuthenticateCustomerUseCase.InputValues(auth);
    }
}
