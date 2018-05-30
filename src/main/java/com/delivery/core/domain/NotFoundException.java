package com.delivery.core.domain;

public class NotFoundException extends DomainException {
    public NotFoundException(String message) {
        super(message);
    }
}
