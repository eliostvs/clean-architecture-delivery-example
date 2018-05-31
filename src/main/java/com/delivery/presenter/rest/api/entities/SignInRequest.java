package com.delivery.presenter.rest.api.entities;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class SignInRequest {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    @Size(min = 6, max = 50)
    private final String password;
}
