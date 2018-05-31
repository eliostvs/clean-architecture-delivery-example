package com.delivery.presenter.usecases.security;

import com.delivery.presenter.rest.api.entities.SignInRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public final class AuthenticateCustomerUseCaseInputMapper {
    public static UsernamePasswordAuthenticationToken map(SignInRequest signInRequest) {
        return new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),
                signInRequest.getPassword());
    }
}
