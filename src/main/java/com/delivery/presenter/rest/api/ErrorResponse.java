package com.delivery.presenter.rest.api;

import lombok.Value;

@Value
class ErrorResponse {
    private final String error = "true";
    private final String message;
}
