package com.delivery.presenter.rest.api.entities;

import lombok.Value;

@Value
public class ApiResponse {
    private final Boolean success;
    private final String message;
}
