package com.delivery.presenter.rest.api.entities;

import lombok.Value;

@Value
public class ErrorResponse {
    private final String error = "true";
    private final String message;
}
