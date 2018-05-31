package com.delivery.presenter.rest.api.entities;

import lombok.Value;

@Value
public class AuthenticationResponse {
    private boolean success = true;
    private String token;
}
