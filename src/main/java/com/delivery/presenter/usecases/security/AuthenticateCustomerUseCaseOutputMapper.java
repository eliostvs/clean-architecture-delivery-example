package com.delivery.presenter.usecases.security;

import com.delivery.presenter.rest.api.entities.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

public final class AuthenticateCustomerUseCaseOutputMapper {
    public static ResponseEntity<AuthenticationResponse> map(String jwtToken) {
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}
