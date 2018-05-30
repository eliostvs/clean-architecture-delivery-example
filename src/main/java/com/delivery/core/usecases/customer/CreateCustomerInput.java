package com.delivery.core.usecases.customer;

import lombok.Value;

@Value
public class CreateCustomerInput {
    private final String name;
    private final String email;
    private final String address;
    private final String password;
}
